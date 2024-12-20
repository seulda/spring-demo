package net.devgrr.springdemo.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${jwt.access.header}")
  private String accessHeader;

  @Value("${jwt.refresh.header}")
  private String refreshHeader;

  @Bean
  public OpenAPI openAPI() {

    OpenAPI openAPI = new OpenAPI();
    openAPI.info(new Info().title("DEMO API").version("1.0").description("DEMO API Documentation"));

    // Security Scheme
    openAPI.addSecurityItem(new SecurityRequirement().addList(accessHeader));
    openAPI.addSecurityItem(new SecurityRequirement().addList(refreshHeader));
    openAPI.components(createJwtComponents());

    return openAPI;
  }

  private Components createJwtComponents() {
    return new Components()
        .addSecuritySchemes(
            accessHeader,
            new SecurityScheme()
                .name(accessHeader)
                .description("Format: {access_token}")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT"))
        .addSecuritySchemes(
            refreshHeader,
            new SecurityScheme()
                .name(refreshHeader)
                .description("Format: Bearer {refresh_token}")
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER));
  }
}
