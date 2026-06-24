package com.biblioteca.msnotificaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Notificaciones - Biblioteca Digital")
                        .version("1.0.0")
                        .description("Microservicio de gestión de notificaciones a usuarios."));
    }
}