package com.quoxsii.telegram.sdbot.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlwaysonScripts {
    @JsonProperty("Extra options")
    private Container extraOptions = new Container();
    @JsonProperty("NudeNet NSFW Censor")
    private Container nudeNet = new Container();
    @JsonProperty("Hypertile")
    private Container hypertile = new Container();
    @JsonProperty("Refiner")
    private Container refiner = new Container(new Object[]{false, "", 0.8});
    @JsonProperty("Seed")
    private Container seed = new Container(new Object[]{-1, false, -1, 0, 0, 0});
    @JsonProperty("Translate prompt to english")
    private Container translatePromptToEnglish = new Container(new Object[]{10});

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Container {
        private Object[] args = new Object[]{};
    }
}
