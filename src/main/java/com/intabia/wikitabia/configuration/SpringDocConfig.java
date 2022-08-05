package com.intabia.wikitabia.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

/**
 * класс конфигурации swagger.
 */
@Configuration
public class SpringDocConfig {
  private static final String BASIC = "basic";
  private static final String OAUTH2_WITH_AUTHORIZATION_CODE_FLOW = "keycloak authorization code";
  private static final String BEARER_JWT = "keycloak jwt";

  @Value("${wikitabia.server.url:http://localhost:8080}")
  private String serverUrl;

  @Value("${wikitabia.keycloak.url:http://localhost:8484/auth}")
  private String keycloakUrl;

  @Value("${wikitabia.keycloak.realm:wikitabia-realm}")
  private String keycloakRealm;

  /**
   * бин, используемый springdoc.
   *
   * @return возвращает инстанс OpenAPI
   */
  @Bean
  public OpenAPI wikitabiaOpenApi() {
    return new OpenAPI()
        .addServersItem(getServer())
        .components(getComponents())
        .addSecurityItem(getSecurityRequirement())
        .info(getApiInfo())
        .externalDocs(getGithubProject());
  }

  private Server getServer() {
    return new Server()
        .url(serverUrl);
  }

  private SecurityRequirement getSecurityRequirement() {
    return new SecurityRequirement()
        .addList(BASIC)
        .addList(OAUTH2_WITH_AUTHORIZATION_CODE_FLOW)
        .addList(BEARER_JWT);
  }

  private Components getComponents() {
    return new Components()
        .addSecuritySchemes(BASIC, getBasicSecurityScheme())
        .addSecuritySchemes(OAUTH2_WITH_AUTHORIZATION_CODE_FLOW,
            getOauth2WithAuthorizationCodeFlowSecurityScheme())
        .addSecuritySchemes(BEARER_JWT, getBearerJwtSecurityScheme());
  }

  private SecurityScheme getBasicSecurityScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("basic");
  }

  private SecurityScheme getOauth2WithAuthorizationCodeFlowSecurityScheme() {
    OAuthFlows authorizationCodeFlow =
        new OAuthFlows()
            .authorizationCode(new OAuthFlow()
                .authorizationUrl(
                    keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/auth")
                .tokenUrl(
                    keycloakUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token")
                .scopes(new Scopes()
                    .addString("openid", "")));
    return new SecurityScheme()
        .type(SecurityScheme.Type.OAUTH2)
        .flows(authorizationCodeFlow)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");
  }

  private SecurityScheme getBearerJwtSecurityScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");
  }

  private Info getApiInfo() {
    return new Info()
        .title("Wikitabia API")
        .description("Wikitabia - веб сервер для сохранения "
            + "полезных ссылок на технические материалы "
            + "с возможностью маркировать ссылку некоторым тэгом "
            + "и в дальнейшем получать по тэгам полезные материалы.")
        .version("v0.1.0");
  }

  private ExternalDocumentation getGithubProject() {
    return new ExternalDocumentation()
        .description("Wikitabia github")
        .url("https://github.com/intabia-intership/wikitabia-server");
  }

  /**
   * бин конфигурации springdoc properties.
   *
   * @return возвращает инстанс SpringDocConfigProperties
   */
  @Primary
  @Bean
  public SpringDocConfigProperties propertiesConfig() {
    SpringDocConfigProperties configProperties = new SpringDocConfigProperties();
    configProperties.setPathsToMatch(List.of("/api/**", "/tag/**"));
    configProperties.setDefaultProducesMediaType(MediaType.APPLICATION_JSON_VALUE);
    return configProperties;
  }
}