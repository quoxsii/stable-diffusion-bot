package com.quoxsii.telegram.sdbot.callback.inlinebutton.seed;

import com.quoxsii.telegram.sdbot.aspect.annotation.ChatAction;
import com.quoxsii.telegram.sdbot.callback.inlinebutton.SdBotInlineButton;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class SdBotResetSeedInlineButton implements SdBotInlineButton {

    private final String identifier = SdBotUiComponent.INLINE_KEYBOARD_BUTTON_RESET_SEED.toString();

    @Override
    @SneakyThrows
    @ChatAction(ActionType.TYPING)
    public void processMessage(AbsSender absSender, CallbackQuery callbackQuery, BotUser botUser) {
        botUser.setUseSeed(false);
        botUser.setDialogState(null);

        answerAndDeleteMessage(absSender, callbackQuery, botUser);

        absSender.executeAsync(SendMessage.builder()
                .chatId(botUser.getId())
                .text(I18n.getProperty(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_RESET_SEED, botUser.getLanguage()))
                .build());
    }

}