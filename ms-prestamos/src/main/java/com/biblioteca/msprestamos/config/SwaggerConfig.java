package com.biblioteca.msprestamos.config;

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
                        .title("MS Préstamos - Biblioteca Digital")
                        .version("1.0.0")
                        .description("Microservicio de gestión de préstamos. " +
                                "Regla de negocio: no se permite préstamo si usuario tiene multas pendientes."));
    }
}