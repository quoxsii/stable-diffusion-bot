package com.quoxsii.telegram.sdbot.controller;

import com.quoxsii.telegram.sdbot.SdBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController("/api/v1/bot")
@RequiredArgsConstructor
public class SdBotController {

    private final SdBot sdBot;

    @PostMapping
    public BotApiMethod<?> handleUpdate(@RequestBody Update update) {
        return sdBot.onWebhookUpdateReceived(update);
    }

}
