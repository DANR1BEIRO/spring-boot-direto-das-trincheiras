package academy.devdojo.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConnectionConfig {

    @Bean
    // @Primary
    public Connection connectionMySql() {
        return new Connection("daniel", "ribeiroSQL", "amigo");
    }

    @Bean(name = "testandoOMapeamentoDoBeanName")
    public Connection connectionMongo() {
        return new Connection("daniel", "ribeiroMongoDB", "amigo");
    }
}
