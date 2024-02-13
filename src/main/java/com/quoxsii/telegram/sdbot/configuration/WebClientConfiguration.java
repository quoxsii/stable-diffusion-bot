package com.quoxsii.telegram.sdbot.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Value("${spring.codec.max-in-memory-size}")
    private String maxInMemorySize;

    public HttpClient httpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(15000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(15000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(15000, TimeUnit.MILLISECONDS)));
    }

    @Bean
    public WebClient webClient() {
        int maxInMemorySizeBytes = Math.toIntExact(DataSize.parse(maxInMemorySize).toBytes());
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultRequest(headersSpec -> headersSpec.accept(MediaType.APPLICATION_JSON))
                .clientConnector(new ReactorClientHttpConnector(httpClient()))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(maxInMemorySizeBytes))
                        .build())
                .build();
    }

}
