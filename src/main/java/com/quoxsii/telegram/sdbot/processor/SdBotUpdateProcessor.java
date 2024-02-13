package com.quoxsii.telegram.sdbot.processor;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Getter
@Setter
@NoArgsConstructor
public abstract class SdBotUpdateProcessor {

    private SdBotUpdateProcessor nextProcessor;

    public abstract void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser);

    public void next(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (nextProcessor != null) {
            nextProcessor.process(absSender, update, botUser);
        }
    }

}
