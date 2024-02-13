package com.quoxsii.telegram.sdbot.callback.inlinebutton;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.repository.BotUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotCancelInlineButton implements SdBotInlineButton {

    private final String identifier = SdBotUiComponent.INLINE_KEYBOARD_BUTTON_CANCEL.toString();

    private final BotUserRepository botUserRepository;

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, CallbackQuery callbackQuery, BotUser botUser) {
        botUser.setDialogId(null);
        botUser.setDialogState(null);
        answerAndDeleteMessage(absSender, callbackQuery, botUser);
    }

}
