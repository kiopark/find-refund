package szs.findrefund.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static szs.findrefund.common.Constants.*;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
                    .baseUrl(UrlConst.SCRAP_URL)
                    .build();
  }
}