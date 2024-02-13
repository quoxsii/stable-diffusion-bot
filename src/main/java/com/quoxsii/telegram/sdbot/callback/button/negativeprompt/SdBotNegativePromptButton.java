package com.quoxsii.telegram.sdbot.callback.button.negativeprompt;

import com.quoxsii.telegram.sdbot.aspect.annotation.ChatAction;
import com.quoxsii.telegram.sdbot.callback.button.SdBotButton;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.markup.SdBotMarkupFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Set;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotNegativePromptButton implements SdBotButton {

    private final Set<String> identifier = I18n.getProperties(SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT);

    @Override
    @SneakyThrows
    @ChatAction(ActionType.TYPING)
    public void processMessage(AbsSender absSender, Message message, BotUser botUser) {
        botUser.setDialogState(SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT);

        Long chatId = message.getChatId();

        absSender.executeAsync(DeleteMessage.builder()
                .chatId(botUser.getId())
                .messageId(message.getMessageId())
                .build());

        absSender.executeAsync(SendMessage.builder()
                .chatId(chatId)
                .text(I18n.getProperty(SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT_RESPONSE, botUser.getLanguage()))
                .replyMarkup(SdBotMarkupFactory.negativePromptMarkup(botUser))
                .build());
    }

}
