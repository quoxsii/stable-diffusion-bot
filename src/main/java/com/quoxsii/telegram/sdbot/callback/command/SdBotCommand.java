package com.quoxsii.telegram.sdbot.callback.command;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface SdBotCommand {

    String getIdentifier();

    String getDescription();

    void processMessage(AbsSender absSender, Message message, BotUser botUser);

}
