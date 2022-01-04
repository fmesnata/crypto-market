package fm.fmesnata.cryptomarket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient coincapClient(@Value("${coincap.url}") String coincapUrl){
        return WebClient.builder()
                .baseUrl(coincapUrl)
                .build();
    }
}
