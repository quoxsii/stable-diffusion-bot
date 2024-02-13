package com.quoxsii.telegram.sdbot.processor.impl;

import com.quoxsii.telegram.sdbot.callback.dialog.SdBotDialog;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.function.BiPredicate;

public class SdBotDialogProcessor extends SdBotUpdateProcessor {

    private final static BiPredicate<Update, BotUser> shouldProcess = (update, botUser) -> update.hasMessage() && update.getMessage().hasText() && botUser.hasDialogId() && botUser.hasDialogState();

    private final List<SdBotDialog> botDialogs;

    public SdBotDialogProcessor(List<SdBotDialog> botDialogs) {
        this.botDialogs = botDialogs;
    }

    @Override
    public void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (shouldProcess.test(update, botUser)) {
            botDialogs.stream()
                    .filter(d -> d.getIdentifier().equals(botUser.getDialogState()))
                    .findFirst()
                    .ifPresentOrElse(
                            d -> d.processMessage(absSender, update.getMessage(), botUser),
                            () -> {
                                botUser.setDialogState(null);
                                next(absSender, update, botUser);
                            });
        } else {
            next(absSender, update, botUser);
        }
    }

}
