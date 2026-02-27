package academy.devdojo.config;

import external.dependency.ExternalWeatherApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalWeatherApiConfig {

    @Bean
    public ExternalWeatherApi createExternalWeatherApi() {
        return new ExternalWeatherApi();
    }
}
