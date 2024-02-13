package com.quoxsii.telegram.sdbot.callback.dialog;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface SdBotDialog {

    SdBotUiComponent getIdentifier();

    void processMessage(AbsSender absSender, Message message, BotUser botUser);

}
