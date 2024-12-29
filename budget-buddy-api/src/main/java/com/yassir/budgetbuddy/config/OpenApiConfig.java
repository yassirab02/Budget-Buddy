package com.yassir.budgetbuddy.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Yassir",
                        email = "yassirabourial86@gmail.com",
                        url = "https://budgetBuddy.com/contact"
                ),
                description = "OpenApi documentation for spring security",
                title = "OpenApi specification -Yassir ",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms for service"

        ),
        servers = {
                @Server(
                        description = "Local environment",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "Prod environment",
                        url = "http://prod-url/api/v1"
                )

        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth", // should be exactly as the name in the @SecurityRequirement
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
