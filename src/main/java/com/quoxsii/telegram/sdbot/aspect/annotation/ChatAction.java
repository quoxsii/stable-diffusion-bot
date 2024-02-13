package com.quoxsii.telegram.sdbot.aspect.annotation;

import org.telegram.telegrambots.meta.api.methods.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChatAction {

    ActionType value();

}
