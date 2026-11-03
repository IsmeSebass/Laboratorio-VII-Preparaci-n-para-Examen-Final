package com.laboratorio.empleados.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuracion de Swagger, se agrega el boton Authorize para mandar la API Key en el header
@Configuration
public class OpenApiConfig {

    private static final String API_KEY = "ApiKey";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Empleados")
                        .description("Laboratorio VII - API para administrar empleados")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes(API_KEY, new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("X-API-KEY")))
                .addSecurityItem(new SecurityRequirement().addList(API_KEY));
    }
}
