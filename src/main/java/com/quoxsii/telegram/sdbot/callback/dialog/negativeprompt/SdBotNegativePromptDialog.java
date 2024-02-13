package com.quoxsii.telegram.sdbot.callback.dialog.negativeprompt;

import com.quoxsii.telegram.sdbot.callback.dialog.SdBotDialog;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotNegativePromptDialog implements SdBotDialog {

    private final SdBotUiComponent identifier = SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT;

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message, BotUser botUser) {
        Long chatId = message.getChatId();

        botUser.setNegativePrompt(message.getText());
        botUser.setDialogState(null);

        absSender.executeAsync(DeleteMessage.builder()
                .chatId(botUser.getId())
                .messageId(message.getMessageId())
                .build());

        String propertyText = I18n.getProperty(SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT, botUser.getLanguage());

        absSender.executeAsync(SendMessage.builder()
                .chatId(chatId)
                .text(String.format("%s: %s", propertyText, botUser.getNegativePrompt()))
                .build());
    }

}
