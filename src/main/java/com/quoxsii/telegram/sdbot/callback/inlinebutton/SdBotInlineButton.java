package com.quoxsii.telegram.sdbot.callback.inlinebutton;

import com.quoxsii.telegram.sdbot.entity.BotUser;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface SdBotInlineButton {

    /**
     * Get the inline button identifier
     *
     * @return the inline button identifier
     */
    String getIdentifier();

    /**
     * Process the callback query
     *
     * @param absSender absSender to send messages over
     * @param callbackQuery   the callback query to process
     */
    void processMessage(AbsSender absSender, CallbackQuery callbackQuery, BotUser botUser);

    @SneakyThrows
    default void answerAndDeleteMessage(AbsSender absSender, CallbackQuery callbackQuery, BotUser botUser) {
        Integer messageId = ((Message) callbackQuery.getMessage()).getMessageId();

        absSender.executeAsync(AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .build());

        absSender.executeAsync(DeleteMessage.builder()
                .chatId(botUser.getId())
                .messageId(messageId)
                .build());
    }

}
