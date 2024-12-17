package com.example.Genius.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.*;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("github",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(
                                                        new OAuthFlow()
                                                                .authorizationUrl("https://github.com/login/oauth/authorize")
                                                                .tokenUrl("http://localhost:8080/oauth2/github/token")
                                                                .scopes(new Scopes()
                                                                        .addString("read:user", "Read user profile")
                                                                        .addString("repo", "Repository access")
                                                                )
                                                )
                                        )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("github"));
    }
}


