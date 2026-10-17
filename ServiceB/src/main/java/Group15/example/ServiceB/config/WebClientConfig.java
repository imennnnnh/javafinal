package Group15.example.ServiceB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //Service A (Menu Service) 的網址
    private static final String SERVICE_A_URL = "http://localhost:8080"; 

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    @Bean
    public WebClient serviceAClient(WebClient.Builder builder) {
        return builder.baseUrl(SERVICE_A_URL).build();
    }
}