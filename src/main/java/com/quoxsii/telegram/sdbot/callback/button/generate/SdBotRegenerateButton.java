package com.quoxsii.telegram.sdbot.callback.button.generate;

import com.quoxsii.telegram.sdbot.callback.button.SdBotButton;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.request.Txt2ImgRequester;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Set;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotRegenerateButton implements SdBotButton {

    private final Set<String> identifier = I18n.getProperties(SdBotUiComponent.KEYBOARD_BUTTON_REGENERATE);

    private final Txt2ImgRequester txt2ImgRequester;

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message, BotUser botUser) {
        absSender.executeAsync(DeleteMessage.builder()
                .chatId(botUser.getId())
                .messageId(message.getMessageId())
                .build());

        txt2ImgRequester.generateImage(absSender, message, botUser, true);
    }

}
