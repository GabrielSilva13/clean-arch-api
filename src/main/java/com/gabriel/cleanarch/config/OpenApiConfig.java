package com.gabriel.cleanarch.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Returns an OpenAPI bean for the API documentation.
     * 
     * This bean is used by SpringDoc to generate the OpenAPI documentation.
     * 
     * @return an OpenAPI bean.
     */
    @Bean
    public OpenAPI apiDocs() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clean Arch API - Tasks")
                        .description("API REST em Java com Spring Boot, JWT e PostgreSQL")
                        .version("1.0.0")
                );
    }
}
