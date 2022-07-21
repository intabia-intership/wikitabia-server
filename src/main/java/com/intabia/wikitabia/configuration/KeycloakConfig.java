package com.intabia.wikitabia.configuration;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * класс конфигурации keycloak.
 */
@Configuration
public class KeycloakConfig {
  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
