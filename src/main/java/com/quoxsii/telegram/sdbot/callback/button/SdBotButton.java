package com.quoxsii.telegram.sdbot.callback.button;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Set;

public interface SdBotButton {

    /**
     * Get set of button identifiers
     *
     * @return set of button identifiers
     */
    Set<String> getIdentifier();

    /**
     * Process the message
     *
     * @param absSender absSender to send messages over
     * @param message   the message to process
     */
    void processMessage(AbsSender absSender, Message message, BotUser botUser);

}
