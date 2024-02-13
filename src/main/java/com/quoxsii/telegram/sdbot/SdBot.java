package com.quoxsii.telegram.sdbot;

import com.quoxsii.telegram.sdbot.callback.command.SdBotCommand;
import com.quoxsii.telegram.sdbot.configuration.SdBotEnvironmentConfiguration;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.mapper.BotCommandMapper;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessorFactory;
import com.quoxsii.telegram.sdbot.repository.BotUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class SdBot extends TelegramWebhookBot {

    private final WebClient webClient;
    private final SdBotEnvironmentConfiguration environmentConfiguration;
    private final List<SdBotCommand> botCommands;
    private final BotCommandMapper botCommandMapper;
    private final SdBotUpdateProcessor updateProcessor;
    private final BotUserRepository botUserRepository;

    public SdBot(DefaultBotOptions options,
                               WebClient webClient,
                               SdBotEnvironmentConfiguration environmentConfiguration,
                               List<SdBotCommand> botCommands,
                               BotCommandMapper botCommandMapper,
                               SdBotUpdateProcessorFactory updateProcessorFactory,
                               BotUserRepository botUserRepository) throws TelegramApiException {
        super(options, environmentConfiguration.getToken());
        this.webClient = webClient;
        this.environmentConfiguration = environmentConfiguration;
        this.botCommands = botCommands;
        this.botCommandMapper = botCommandMapper;
        this.updateProcessor = updateProcessorFactory.defaultChain();
        this.botUserRepository = botUserRepository;
        init();
    }

    private void init() throws TelegramApiException {
        initWebhook();
        initBotCommands();
    }

    private void initWebhook() throws TelegramApiException {
        String url = getWebhookInfo().getUrl();
        String botWebhookPath = environmentConfiguration.getBotWebhookPath();

        if (!Objects.equals(url, botWebhookPath)) {
            SetWebhook setWebhook = new SetWebhook();
            setWebhook.setUrl(botWebhookPath);
            setWebhook(setWebhook);
        }
    }

    private void initBotCommands() throws TelegramApiException {
        Collection<BotCommand> menuCommands = botCommandMapper.toMenuCommand(botCommands);
        execute(SetMyCommands.builder()
                .commands(menuCommands)
                .build());
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            getSenderId(update).ifPresent(id -> {
                BotUser botUser = botUserRepository.findById(id).orElseGet(() -> new BotUser(id));
                updateProcessor.process(this, update, botUser);
                botUserRepository.save(botUser);
            });
        } catch (Exception e) {
            log.error("Ошибка при обработке update'а: ", e);
        }
        return null;
    }

    private Optional<Long> getSenderId(Update update) {
        Optional<Message> message = Optional.ofNullable(update.getMessage());
        Optional<CallbackQuery> callbackQuery = Optional.ofNullable(update.getCallbackQuery());
        return message
                .map(Message::getFrom)
                .or(() -> callbackQuery.map(CallbackQuery::getFrom))
                .filter(u -> !u.getIsBot())
                .map(User::getId);
    }

    @Override
    public String getBotPath() {
        return environmentConfiguration.getBotWebhookPath();
    }

    @Override
    public String getBotUsername() {
        return environmentConfiguration.getBotUsername();
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) {
        webClient.post()
                .uri(getOptions().getBaseUrl(), uriBuilder -> uriBuilder
                        .path(environmentConfiguration.getToken())
                        .pathSegment(SetWebhook.PATH)
                        .build())
                .bodyValue(setWebhook)
                .retrieve()
                .toBodilessEntity()
                .doOnError(e -> log.error(e.getCause().getMessage()))
                .doOnSuccess(r -> log.info("Webhook path set to {}", setWebhook.getUrl()))
                .block();
    }

}
