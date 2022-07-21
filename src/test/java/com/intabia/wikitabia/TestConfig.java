package com.intabia.wikitabia;

import com.intabia.wikitabia.controller.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("controller-test")
public class TestConfig extends WebSecurityConfigurerAdapter {
  private static final String REGISTRATION_URL = "/api/user";
  private static final String TELEGRAM_LOGIN_URL = "/api/telegram-login";

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
        .antMatchers(TELEGRAM_LOGIN_URL).permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .requestCache()
        .requestCache(new NullRequestCache());
  }

  @Bean
  GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }
}
