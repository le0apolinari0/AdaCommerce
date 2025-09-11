package br.com.AdaCommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${openfoodfacts.api.base-url}")
    private String openFoodFactsBaseUrl;

    @Bean("openFoodFactsWebClient")
    public WebClient openFoodFactsWebClient() {
        return WebClient.builder()
                .baseUrl(openFoodFactsBaseUrl)
                .build();
    }

    @Bean("viaCepWebClient")
    public WebClient viaCepWebClient() {
        return WebClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }
}
