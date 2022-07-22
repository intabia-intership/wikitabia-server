package com.intabia.wikitabia.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * класс конфигурации swagger.
 */
@Configuration
public class SpringDocConfig {
  /**
   * бин, используемый springdoc.
   *
   * @return возвращает инстанс OpenAPI
   */
  @Bean
  public OpenAPI wikitabiaOpenApi() {
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("wikitabia basic", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic"))
            .addSecuritySchemes("wikitabia keycloak", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")))
        .info(new Info().title("Wikitabia API")
            .description("Wikitabia - веб сервер для сохранения "
                + "полезных ссылок на технические материалы "
                + "с возможностью маркировать ссылку некоторым тэгом "
                + "и в дальнейшем получать по тэгам полезные материалы.")
            .version("v0.1.0"))
        .externalDocs(new ExternalDocumentation()
            .description("Wikitabia github")
            .url("https://github.com/intabia-intership/wikitabia-server"));
  }
}