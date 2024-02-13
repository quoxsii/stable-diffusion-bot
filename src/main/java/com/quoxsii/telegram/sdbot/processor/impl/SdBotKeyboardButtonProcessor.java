package com.quoxsii.telegram.sdbot.processor.impl;

import com.quoxsii.telegram.sdbot.callback.button.SdBotButton;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.function.BiPredicate;

public class SdBotKeyboardButtonProcessor extends SdBotUpdateProcessor {

    private final static BiPredicate<Update, BotUser> shouldProcess = (update, botUser) -> update.hasMessage() && update.getMessage().hasText();

    private final List<SdBotButton> botButtons;

    public SdBotKeyboardButtonProcessor(List<SdBotButton> botButtons) {
        this.botButtons = botButtons;
    }

    @Override
    public void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (shouldProcess.test(update, botUser)) {
            botButtons.stream()
                    .filter(b -> b.getIdentifier().contains(update.getMessage().getText()))
                    .findFirst()
                    .ifPresentOrElse(
                            b -> b.processMessage(absSender, update.getMessage(), botUser),
                            () -> next(absSender, update, botUser));
        } else {
            next(absSender, update, botUser);
        }
    }

}
