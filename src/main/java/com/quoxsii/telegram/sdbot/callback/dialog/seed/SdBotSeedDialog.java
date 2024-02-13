package com.quoxsii.telegram.sdbot.callback.dialog.seed;

import com.quoxsii.telegram.sdbot.callback.dialog.SdBotDialog;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.markup.SdBotMarkupFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotSeedDialog implements SdBotDialog {

    private final SdBotUiComponent identifier = SdBotUiComponent.KEYBOARD_BUTTON_SEED;

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message, BotUser botUser) {
        Long chatId = message.getChatId();

        try {
            Long seed = Long.valueOf(message.getText());

            absSender.executeAsync(DeleteMessage.builder()
                    .chatId(botUser.getId())
                    .messageId(message.getMessageId())
                    .build());

            absSender.executeAsync(DeleteMessage.builder()
                    .chatId(botUser.getId())
                    .messageId(botUser.getDialogId())
                    .build());

            botUser.setSeed(seed);
            botUser.setUseSeed(true);
            botUser.setDialogId(null);
            botUser.setDialogState(null);

            String propertyText = I18n.getProperty(SdBotUiComponent.KEYBOARD_BUTTON_SEED, botUser.getLanguage());

            absSender.executeAsync(SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format("%s: %d", propertyText, botUser.getSeed()))
                    .build());
        } catch (NumberFormatException e) {
            absSender.executeAsync(DeleteMessage.builder()
                    .chatId(botUser.getId())
                    .messageId(message.getMessageId())
                    .build());

            String text = String.format("%s\n\n%s",
                    I18n.getProperty(SdBotUiComponent.DIALOG_SEED_ERROR_NUMBER_FORMAT, botUser.getLanguage()),
                    I18n.getProperty(SdBotUiComponent.KEYBOARD_BUTTON_SEED_RESPONSE, botUser.getLanguage()));

            absSender.executeAsync(EditMessageText.builder()
                    .chatId(botUser.getId())
                    .messageId(botUser.getDialogId())
                    .text(text)
                    .replyMarkup(SdBotMarkupFactory.seedMarkup(botUser))
                    .build());

            /*absSender.executeAsync(SendMessage.builder()
                    .chatId(chatId)
                    .text(I18n.getProperty(SdBotUiComponent.DIALOG_SEED_ERROR_NUMBER_FORMAT, botUser.getLanguage()))
                    .build());*/
        }
    }

}
