package com.intabia.wikitabia.configuration;

import com.intabia.wikitabia.configuration.filter.IgnoreBasicAuthRequestHeaderRequestMatcher;
import com.intabia.wikitabia.service.implementation.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.QueryParamPresenceRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * класс конфигурации авторизации и аутентификации с помощью keycloak и basic auth.
 */
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
  private static final String REGISTRATION_URL = "/api/user";
  private static final String TELEGRAM_LOGIN_URL = "/api/telegram-login";
  private static final String DEFAULT_LOGIN_URL = "/sso/login";
  private static final String EVERY_URL = "/**";
  private static final String[] SWAGGER_WHITELIST = {
      // -- Swagger UI v2
      "/v2/api-docs",
      "/swagger-resources",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**",
      // -- Swagger UI v3 (OpenAPI)
      "/v3/api-docs/**",
      "/swagger-ui/**"
  };

  private final UserDetailsServiceImpl userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  /**
   * метод, настраивающий аутентификации.
   *
   * @param auth - AuthenticationManagerBuilder, позволяет настраивать аутентификацию
   * @throws Exception если произошла ошибка при добавлении UserDetailsService
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();

    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
    auth.authenticationProvider(keycloakAuthenticationProvider);
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http
        .csrf().disable()
        .cors().disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
          .antMatchers(TELEGRAM_LOGIN_URL).permitAll()
          .antMatchers(SWAGGER_WHITELIST).permitAll()
          .antMatchers(HttpMethod.OPTIONS, EVERY_URL).permitAll()
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
  @Override
  protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter()
      throws Exception {
    RequestMatcher requestMatcher =
        new OrRequestMatcher(
            new AntPathRequestMatcher(DEFAULT_LOGIN_URL),
            new QueryParamPresenceRequestMatcher(OAuth2Constants.ACCESS_TOKEN),
            new IgnoreBasicAuthRequestHeaderRequestMatcher()
        );
    return new KeycloakAuthenticationProcessingFilter(authenticationManagerBean(), requestMatcher);
  }
}
