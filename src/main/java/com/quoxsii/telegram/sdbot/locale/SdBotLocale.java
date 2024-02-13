package com.quoxsii.telegram.sdbot.locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum SdBotLocale {

    ENGLISH("English", Locale.ROOT),
    RUSSIAN("Русский", new Locale("ru", "RU"));

    private final String text;
    private final Locale locale;

}
