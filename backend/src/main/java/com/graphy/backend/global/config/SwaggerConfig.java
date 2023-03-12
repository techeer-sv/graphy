package com.graphy.backend.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 접속 주소 : http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.swagger-ui.version}") String springdocVersion) {
        Info info = new Info()
                .title("Graphy")
                .version(springdocVersion)
                .description("Graphy-API");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}