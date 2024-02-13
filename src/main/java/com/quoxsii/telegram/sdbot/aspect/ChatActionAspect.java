package com.quoxsii.telegram.sdbot.aspect;

import com.quoxsii.telegram.sdbot.aspect.annotation.ChatAction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Aspect
@Component
public class ChatActionAspect {

    @Before(value = "execution(public * *(..)) && args(absSender,message,..) && @annotation(chatAction)", argNames = "absSender,message,chatAction")
    public void sendChatAction(AbsSender absSender, Message message, ChatAction chatAction) {
        try {
            absSender.execute(SendChatAction.builder()
                    .chatId(message.getChatId())
                    .action(chatAction.value().toString())
                    .build());
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить действие", e);
        }
    }

    @Before(value = "execution(public * *(..)) && args(absSender,callbackQuery,..) && @annotation(chatAction)", argNames = "absSender,callbackQuery,chatAction")
    public void sendChatAction(AbsSender absSender, CallbackQuery callbackQuery, ChatAction chatAction) {
        try {
            absSender.execute(SendChatAction.builder()
                    .chatId(callbackQuery.getFrom().getId())
                    .action(chatAction.value().toString())
                    .build());
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить действие", e);
        }
    }

}
