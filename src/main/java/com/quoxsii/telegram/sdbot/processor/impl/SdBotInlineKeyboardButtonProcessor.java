package com.quoxsii.telegram.sdbot.processor.impl;

import com.quoxsii.telegram.sdbot.callback.inlinebutton.SdBotInlineButton;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.function.BiPredicate;

public class SdBotInlineKeyboardButtonProcessor extends SdBotUpdateProcessor {

    private final static BiPredicate<Update, BotUser> shouldProcess = (update, botUser) -> update.hasCallbackQuery() && update.getCallbackQuery().getData() != null;

    private final List<SdBotInlineButton> botInlineButtons;

    public SdBotInlineKeyboardButtonProcessor(List<SdBotInlineButton> botInlineButtons) {
        this.botInlineButtons = botInlineButtons;
    }

    @Override
    public void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (shouldProcess.test(update, botUser)) {
            botInlineButtons.stream()
                    .filter(ib -> ib.getIdentifier().equals(update.getCallbackQuery().getData()))
                    .findFirst()
                    .ifPresentOrElse(
                            ib -> ib.processMessage(absSender, update.getCallbackQuery(), botUser),
                            () -> next(absSender, update, botUser));
        } else {
            next(absSender, update, botUser);
        }
    }

}
