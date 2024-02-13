package com.quoxsii.telegram.sdbot.request.model;

import lombok.Data;

import java.util.List;

@Data
public class Txt2ImgErrorDto {

    private List<Detail> detail;

    @Data
    public static class Detail {
        private List<String> loc;
        private String msg;
        private String type;
    }

}