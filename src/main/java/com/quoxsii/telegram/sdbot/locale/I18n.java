package com.quoxsii.telegram.sdbot.locale;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public final class I18n {

    private static final String bundleName = "lang";

    public static String getProperty(SdBotUiComponent botUiComponent, SdBotLocale botLocale) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, botLocale.getLocale(), new Utf8Control());
        return bundle.getString(botUiComponent.getKey());
    }

    public static Set<String> getProperties(SdBotUiComponent botUiComponent) {
        return Arrays.stream(SdBotLocale.values())
                .map(bl -> ResourceBundle.getBundle(bundleName, bl.getLocale(), new Utf8Control()))
                .map(b -> b.getString(botUiComponent.getKey()))
                .collect(Collectors.toSet());
    }

}
