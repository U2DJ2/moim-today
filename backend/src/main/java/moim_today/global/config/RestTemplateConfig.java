package moim_today.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final CorsInterceptor corsInterceptor;

    public RestTemplateConfig(final CorsInterceptor corsInterceptor) {
        this.corsInterceptor = corsInterceptor;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(corsInterceptor);
        return restTemplate;
    }
}
