package com.quoxsii.telegram.sdbot.locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SdBotUiComponent {

    // START COMMAND
    COMMAND_START_RESPONSE("command.start.response"),

    // GENERATION
    RESPONSE_ERROR_IMAGE_GENERATION("response.error.image-generation"),
    KEYBOARD_BUTTON_REGENERATE("keyboard.button.regenerate"),

    // NEGATIVE PROMPT
    KEYBOARD_BUTTON_NEGATIVE_PROMPT("keyboard.button.negative-prompt"),
    KEYBOARD_BUTTON_NEGATIVE_PROMPT_RESPONSE("keyboard.button.negative-prompt.response"),
    INLINE_KEYBOARD_BUTTON_RESET_NEGATIVE_PROMPT("inline.keyboard.button.negative-prompt.reset"),

    // SEED
    KEYBOARD_BUTTON_SEED("keyboard.button.seed"),
    KEYBOARD_BUTTON_SEED_RESPONSE("keyboard.button.seed.response"),
    INLINE_KEYBOARD_BUTTON_USE_LAST_SEED("inline.keyboard.button.seed.use-last"),
    INLINE_KEYBOARD_BUTTON_RESET_SEED("inline.keyboard.button.seed.reset"),
    DIALOG_SEED_ERROR_NUMBER_FORMAT("dialog.seed.error.number-format"),

    // LANGUAGE
    KEYBOARD_BUTTON_LANGUAGE("keyboard.button.language"),
    INLINE_KEYBOARD_BUTTON_ENGLISH("inline.keyboard.button.language.english"),
    INLINE_KEYBOARD_BUTTON_RUSSIAN("inline.keyboard.button.language.russian"),

    // COMMON
    INLINE_KEYBOARD_BUTTON_CANCEL("inline.keyboard.button.cancel");

    private final String key;

}
