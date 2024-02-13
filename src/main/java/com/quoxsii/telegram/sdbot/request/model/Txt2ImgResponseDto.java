package com.quoxsii.telegram.sdbot.request.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class Txt2ImgResponseDto {
    private List<String> images;
    private Txt2ImgDto parameters;
    private String info;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Txt2ImgResponseInfoDto {
        private String sdModelHash;
        private long seed;
        private int indexOfFirstImage;
        private String jobTimestamp;
        private List<Long> allSubseeds;
        private List<String> allNegativePrompts;
        private List<String> allPrompts;
        private String negativePrompt;
        private String samplerName;
        private Object denoisingStrength;
        private Object faceRestorationModel;
        private String sdModelName;
        private Object sdVaeHash;
        private Object sdVaeName;
        private long subseed;
        private int height;
        private Object cfgScale;
        private int batchSize;
        private boolean restoreFaces;
        private int seedResizeFromH;
        private List<Long> allSeeds;
        private int seedResizeFromW;
        private int steps;
        private String version;
        private ExtraGenerationParams extraGenerationParams;
        private boolean isUsingInpaintingConditioning;
        private List<String> infotexts;
        private Object subseedStrength;
        private int width;
        private List<Object> styles;
        private String prompt;
        private int clipSkip;
    }
}