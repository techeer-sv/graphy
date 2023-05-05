package com.graphy.backend.global.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChatGPTConfig {

    @Value("${gpt.token}")
    private String token;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(token, Duration.ofSeconds(120));
    }
}
