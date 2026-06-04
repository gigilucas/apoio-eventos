package com.eventos.apoio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Apoio a Eventos Académicos")
                        .description("API REST para gestão de eventos, inscrições, participantes, sessões e emissão de certificados")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipa de Desenvolvimento")
                                .email("dev@eventos-academicos.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
