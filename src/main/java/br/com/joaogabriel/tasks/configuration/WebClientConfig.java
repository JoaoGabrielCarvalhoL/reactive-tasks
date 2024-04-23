package br.com.joaogabriel.tasks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfig {

    @Value("${base.url.via.cep}")
    private String url;

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory(url))
                .build();
    }
}
