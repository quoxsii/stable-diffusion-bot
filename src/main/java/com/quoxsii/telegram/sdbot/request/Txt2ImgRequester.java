package com.quoxsii.telegram.sdbot.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quoxsii.telegram.sdbot.aspect.annotation.ChatAction;
import com.quoxsii.telegram.sdbot.entity.BotUser;
import com.quoxsii.telegram.sdbot.exception.SdApiException;
import com.quoxsii.telegram.sdbot.locale.I18n;
import com.quoxsii.telegram.sdbot.locale.SdBotUiComponent;
import com.quoxsii.telegram.sdbot.request.model.Txt2ImgDto;
import com.quoxsii.telegram.sdbot.request.model.Txt2ImgResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class Txt2ImgRequester {

    private static final String TXT_TO_IMG_PATH = "txt2img";

    @Value("${stable-diffusion.path}")
    private String sdPath;

    private final WebClient webClient;

    @SneakyThrows
    @ChatAction(ActionType.UPLOADPHOTO)
    public void generateImage(AbsSender absSender, Message message, BotUser botUser, boolean useLastPrompt) {
        try {
            Txt2ImgResponseDto txt2ImgResponseDto = sendPrompt(useLastPrompt ? botUser.getPrompt() : message.getText(), botUser);
            byte[] imgByteArray = Base64.decodeBase64(txt2ImgResponseDto.getImages().get(0));
            InputStream inputStream = new ByteArrayInputStream(imgByteArray);
            String photoName = message.getFrom() + "_" + LocalDateTime.now();
            InputFile photo = new InputFile(inputStream, photoName);
            absSender.execute(SendPhoto.builder()
                    .chatId(message.getChatId())
                    .photo(photo)
                    .build());
            String info = txt2ImgResponseDto.getInfo();
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            Txt2ImgResponseDto.Txt2ImgResponseInfoDto infoDto = mapper.readValue(info, Txt2ImgResponseDto.Txt2ImgResponseInfoDto.class);
            botUser.setSeed(infoDto.getSeed());
            botUser.setPrompt(infoDto.getPrompt());
        } catch (SdApiException e) {
            log.error("Ошибка при запросе sendPrompt:", e);
            absSender.execute(SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(I18n.getProperty(SdBotUiComponent.RESPONSE_ERROR_IMAGE_GENERATION, botUser.getLanguage()))
                    .build());
        }
    }

    private Txt2ImgResponseDto sendPrompt(String prompt, BotUser botUser) throws SdApiException {
        return webClient.post()
                .uri(sdPath, uriBuilder -> uriBuilder
                        .pathSegment(TXT_TO_IMG_PATH)
                        .build())
                .bodyValue(Txt2ImgDto.builder()
                        .prompt(prompt)
                        .negativePrompt("" + botUser.getNegativePrompt())
                        .seed(botUser.getUseSeed() ? botUser.getSeed() : -1L)
                        .build())
                .retrieve()
                .bodyToMono(Txt2ImgResponseDto.class)
                .doOnError(e -> log.error(e.getCause().getMessage()))
                .doOnSuccess(r -> log.info("Generated image for prompt {}", prompt))
                .onErrorComplete()
                .blockOptional()
                .orElseThrow(SdApiException::new);
    }

}
