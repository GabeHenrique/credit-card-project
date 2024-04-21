package br.com.f1rst.cartaoapi.infrastructure.configuration;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Swagger: habilitado");

        String serverUrl = "http://localhost:8080";

        return new OpenAPI()
            .addServersItem(new Server().url(serverUrl))
            .info(new Info()
                .title("API Cartão F1RST")
                .version("1.0")
                .description("Api Cartão F1RST")
            );
    }

    @Bean
    public OpenApiCustomizer customizer() {
        Parameter acceptLanguageHeader = new Parameter()
            .in(ParameterIn.HEADER.toString())
            .schema(new StringSchema()._default("pt-BR"))
            .name("Accept-Language")
            .description("identificador de Locale")
            .addExample("Português", new Example().description("Portugues").value("pt-BR"))
            .addExample("Inglês", new Example().description("Inglês").value("en-US"))
            .addExample("Espanhol", new Example().description("Espanhol").value("es-ES"))
            .required(true);
        return openApi -> openApi.getPaths().values().stream()
        .flatMap(pathItem -> pathItem.readOperations().stream())
        .forEach(operation -> operation.addParametersItem(acceptLanguageHeader));
    }
}
