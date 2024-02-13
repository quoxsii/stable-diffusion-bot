package com.quoxsii.telegram.sdbot.markup;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotLocale;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SdBotMarkupFactory {

    public static ReplyKeyboardMarkup startMarkup(SdBotLocale locale) {
        List<KeyboardRow> keyboard = List.of(
                new KeyboardRow(List.of(
                        keyboardButton(SdBotUiComponent.KEYBOARD_BUTTON_REGENERATE, locale))),
                new KeyboardRow(List.of(
                        keyboardButton(SdBotUiComponent.KEYBOARD_BUTTON_NEGATIVE_PROMPT, locale))),
                new KeyboardRow(List.of(
                        keyboardButton(SdBotUiComponent.KEYBOARD_BUTTON_SEED, locale),
                        keyboardButton(SdBotUiComponent.KEYBOARD_BUTTON_LANGUAGE, locale))));

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboard)
                .build();
    }

    public static InlineKeyboardMarkup negativePromptMarkup(BotUser botUser) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(List.of(
                List.of(
                        inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_CANCEL, botUser.getLanguage()))));

        if (botUser.getSeed() != -1L) {
            keyboard.addAll(0, List.of(
                    List.of(
                            inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_RESET_NEGATIVE_PROMPT, botUser.getLanguage()))));
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    public static InlineKeyboardMarkup seedMarkup(BotUser botUser) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>(List.of(
                List.of(
                        inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_CANCEL, botUser.getLanguage()))));

        if (botUser.getSeed() != -1L) {
            keyboard.addAll(0, List.of(
                    List.of(
                            inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_USE_LAST_SEED, botUser.getLanguage())),
                    List.of(
                            inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_RESET_SEED, botUser.getLanguage()))));
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    public static InlineKeyboardMarkup languageMarkup(SdBotLocale locale) {
        List<List<InlineKeyboardButton>> keyboard = List.of(
                List.of(
                        inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_ENGLISH, locale),
                        inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_RUSSIAN, locale)),
                List.of(
                        inlineKeyboardButton(SdBotUiComponent.INLINE_KEYBOARD_BUTTON_CANCEL, locale)));

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    private static KeyboardButton keyboardButton(SdBotUiComponent uiComponent, SdBotLocale locale) {
        return KeyboardButton.builder()
                .text(I18n.getProperty(uiComponent, locale))
                .build();
    }

    private static InlineKeyboardButton inlineKeyboardButton(SdBotUiComponent uiComponent, SdBotLocale locale) {
        return InlineKeyboardButton.builder()
                .text(I18n.getProperty(uiComponent, locale))
                .callbackData(uiComponent.toString())
                .build();
    }

}
