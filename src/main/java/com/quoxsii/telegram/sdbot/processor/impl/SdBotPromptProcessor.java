package com.quoxsii.telegram.sdbot.processor.impl;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.processor.SdBotUpdateProcessor;
import com.quoxsii.telegram.sdbot.request.Txt2ImgRequester;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.function.BiPredicate;

public class SdBotPromptProcessor extends SdBotUpdateProcessor {

    private final static BiPredicate<Update, BotUser> shouldProcess = (update, botUser) -> update.hasMessage() && update.getMessage().hasText();

    private final Txt2ImgRequester txt2ImgRequester;

    public SdBotPromptProcessor(Txt2ImgRequester txt2ImgRequester) {
        this.txt2ImgRequester = txt2ImgRequester;
    }

    @Override
    public void process(@NonNull AbsSender absSender, @NonNull Update update, @NonNull BotUser botUser) {
        if (shouldProcess.test(update, botUser)) {
            txt2ImgRequester.generateImage(absSender, update.getMessage(), botUser, false);
        } else {
            next(absSender, update, botUser);
        }
    }

}
