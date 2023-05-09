package com.graphy.backend.global.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@PropertySource("classpath:application-local.yml")
public class ChatGPTConfig {

    @Value("${gpt.token}")
    private String token;

    public static final String MODEL_NAME = "text-davinci-003";
    public static final int MAX_TOKEN = 3500;

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(token, Duration.ofSeconds(120));
    }
}
