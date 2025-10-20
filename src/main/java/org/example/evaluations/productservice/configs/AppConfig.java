package org.example.evaluations.productservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean //Spring beans are spring objects which are to be managed by the spring container
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }
}
