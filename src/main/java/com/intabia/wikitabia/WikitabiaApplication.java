package com.intabia.wikitabia;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Входная точка приложения wikitabia.
 */
@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories
public class WikitabiaApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(WikitabiaApplication.class).build(args).run();
  }
}
