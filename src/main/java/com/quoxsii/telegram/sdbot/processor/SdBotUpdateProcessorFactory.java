package com.quoxsii.telegram.sdbot.processor;

import com.quoxsii.telegram.sdbot.callback.button.SdBotButton;
import com.quoxsii.telegram.sdbot.callback.command.SdBotCommand;
import com.quoxsii.telegram.sdbot.callback.dialog.SdBotDialog;
import com.quoxsii.telegram.sdbot.callback.inlinebutton.SdBotInlineButton;
import com.quoxsii.telegram.sdbot.processor.impl.*;
import com.quoxsii.telegram.sdbot.request.Txt2ImgRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SdBotUpdateProcessorFactory {

    private final List<SdBotCommand> botCommands;
    private final List<SdBotButton> botButtons;
    private final List<SdBotInlineButton> botInlineButtons;
    private final List<SdBotDialog> botDialogs;
    private final Txt2ImgRequester txt2ImgRequester;

    public SdBotUpdateProcessor defaultChain() {
        return SdBotUpdateProcessorsChainBuilder
                .from(new SdBotCommandProcessor(botCommands))
                .then(new SdBotKeyboardButtonProcessor(botButtons))
                .then(new SdBotInlineKeyboardButtonProcessor(botInlineButtons))
                .then(new SdBotDialogProcessor(botDialogs))
                .then(new SdBotPromptProcessor(txt2ImgRequester))
                .build();
    }

}
