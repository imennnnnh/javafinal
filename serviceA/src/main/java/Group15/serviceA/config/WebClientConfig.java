package Group15.serviceA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // ★★★ 補上這一段！這是產生 Builder 的工廠 ★★★
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // 這是你原本寫的，它需要上面的 builder 才能運作
    @Bean
    public WebClient reviewWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8082/api/reviews").build();
    }
}