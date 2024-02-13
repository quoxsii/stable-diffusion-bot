package com.quoxsii.telegram.sdbot.processor;

import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

public class SdBotUpdateProcessorsChainBuilder {

    private static final BinaryOperator<SdBotUpdateProcessor> ACCUMULATOR = (a, b) -> {
        a.setNextProcessor(b);
        return b;
    };

    private final List<SdBotUpdateProcessor> processors = new LinkedList<>();

    private SdBotUpdateProcessorsChainBuilder(@NonNull SdBotUpdateProcessor firstProcessor) {
        processors.add(firstProcessor);
    }

    public static SdBotUpdateProcessorsChainBuilder from(@NonNull SdBotUpdateProcessor firstProcessor) {
        return new SdBotUpdateProcessorsChainBuilder(firstProcessor);
    }

    public SdBotUpdateProcessorsChainBuilder then(@NonNull SdBotUpdateProcessor sdBotUpdateProcessor) {
        processors.add(sdBotUpdateProcessor);
        return this;
    }

    @NonNull
    public SdBotUpdateProcessor build() {
        boolean foundAny = false;
        SdBotUpdateProcessor result = null;
        for (SdBotUpdateProcessor element : processors){
            if (!foundAny) {
                foundAny = true;
                result = element;
            }
            else result = ACCUMULATOR.apply(result, element);
        }
        return processors.get(0);
    }

}
