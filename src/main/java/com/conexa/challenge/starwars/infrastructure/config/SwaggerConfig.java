package com.conexa.challenge.starwars.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    private static final String TITLE = "SWAPI integration";
    private static final String DESCRIPTION = "Backend service that integrates with the Star Wars public API." +
            "It provides secure JWT authentication and exposes endpoints to list and search People, Films, Starships, " +
            "and Vehicles with pagination and filtering by ID.";

    @Bean
    public OpenAPI OpenAPI() {
        OpenAPI openApi = new OpenAPI();
        openApi.info(
                new Info()
                        .title(TITLE)
                        .description(DESCRIPTION)
        );

        openApi.components(
                new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization"))
        );

        openApi.addSecurityItem(
                new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))
        );

        return openApi;
    }
}
