package com.quoxsii.telegram.sdbot.mapper;

import com.quoxsii.telegram.sdbot.callback.command.SdBotCommand;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BotCommandMapper {

    @Named("toMenuCommand")
    @Mapping(target = "command", source = "identifier")
    BotCommand toMenuCommand(SdBotCommand botCommand);

    @IterableMapping(qualifiedByName = "toMenuCommand")
    List<BotCommand> toMenuCommand(List<SdBotCommand> botCommand);

}
