package com.quoxsii.telegram.sdbot.callback.command.start;

import com.quoxsii.telegram.sdbot.aspect.annotation.ChatAction;
import com.quoxsii.telegram.sdbot.callback.command.SdBotCommand;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.markup.SdBotMarkupFactory;
import com.quoxsii.telegram.sdbot.repository.BotUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotStartCommand implements SdBotCommand {

    private final String identifier = "/start";
    private final String description = "reset";

    private final BotUserRepository botUserRepository;

    @Override
    @SneakyThrows
    @ChatAction(ActionType.TYPING)
    public void processMessage(AbsSender absSender, Message message, BotUser botUser) {
        absSender.execute(SendMessage.builder()
                .chatId(message.getChatId())
                .replyMarkup(SdBotMarkupFactory.startMarkup(botUser.getLanguage()))
                .text(I18n.getProperty(SdBotUiComponent.COMMAND_START_RESPONSE, botUser.getLanguage()))
                .parseMode(ParseMode.HTML)
                .build());
    }

}
