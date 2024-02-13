package com.quoxsii.telegram.sdbot.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Getter
@Component
public class SdBotEnvironmentConfiguration {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.webhook-path}")
    private String botWebhookPath;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.proxy.host:localhost}")
    private String proxyHost;

    @Value("${telegram.bot.proxy.port:80}")
    private Integer proxyPort;

    @Value("${telegram.bot.proxy.type:NO_PROXY}")
    private DefaultBotOptions.ProxyType proxyType;

}
