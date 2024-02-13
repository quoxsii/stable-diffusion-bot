package com.quoxsii.telegram.sdbot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

@Configuration
@RequiredArgsConstructor
public class SdBotConfiguration {

    private final SdBotEnvironmentConfiguration environmentConfiguration;

    @Bean
    public DefaultBotOptions botOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setProxyHost(environmentConfiguration.getProxyHost());
        defaultBotOptions.setProxyPort(environmentConfiguration.getProxyPort());
        defaultBotOptions.setProxyType(environmentConfiguration.getProxyType());
        return defaultBotOptions;
    }

    @Bean
    public SimpleClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (botOptions().getProxyType() != DefaultBotOptions.ProxyType.NO_PROXY) {
            SocketAddress socketAddress = new InetSocketAddress(botOptions().getProxyHost(), botOptions().getProxyPort());
            switch (botOptions().getProxyType()) {
                case HTTP -> requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, socketAddress));
                case SOCKS4, SOCKS5 -> requestFactory.setProxy(new Proxy(Proxy.Type.SOCKS, socketAddress));
            }
        }
        return requestFactory;
    }

}
