package com.wanted.spendtracker.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerUiConfigProperties swaggerUiConfigProperties;
    private final SpringDocConfigProperties springDocConfigProperties;

    @Bean
    public String swaggerPath() {
        return swaggerUiConfigProperties
                .getPath() + "/**";
    }

    @Bean
    public String apiDocsPath() {
        return springDocConfigProperties
                .getApiDocs()
                .getPath() + "/**";
    }

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version,
                           @Value("${springdoc.title}") String title,
                           @Value("${springdoc.description}") String description,
                           @Value("${springdoc.termsOfService}") String termsOfService) {
        Info info = new Info()
                .version(version)
                .title(title)
                .description(description)
                .termsOfService(termsOfService);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
