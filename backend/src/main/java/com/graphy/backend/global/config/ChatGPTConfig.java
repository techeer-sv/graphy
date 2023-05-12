package com.graphy.backend.global.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@PropertySource("classpath:application-docker.yml")
public class ChatGPTConfig {

    @Value("${gpt.token}")
    private String token;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(token, Duration.ofSeconds(120));
    }
}
