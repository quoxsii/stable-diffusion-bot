package com.quoxsii.telegram.sdbot.processor.impl;

import com.quoxsii.telegram.sdbot.callback.command.SdBotCommand;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.function.BiPredicate;

public class SdBotCommandProcessor extends SdBotUpdateProcessor {

    private final static BiPredicate<Update, BotUser> shouldProcess = (update, botUser) -> update.hasMessage() && update.getMessage().isCommand();

    private final List<SdBotCommand> botCommands;

    public SdBotCommandProcessor(List<SdBotCommand> botCommands) {
        this.botCommands = botCommands;
    }

    @Override
    public void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (shouldProcess.test(update, botUser)) {
            botCommands.stream()
                    .filter(d -> d.getIdentifier().equals(update.getMessage().getText()))
                    .findFirst()
                    .ifPresentOrElse(
                            d -> d.processMessage(absSender, update.getMessage(), botUser),
                            () -> next(absSender, update, botUser));
        } else {
            next(absSender, update, botUser);
        }
    }

}
