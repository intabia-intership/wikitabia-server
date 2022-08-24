package com.intabia.wikitabia.configuration.filter;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * реализация RequestMatcher, позволяющая KeycloakAuthenticationProcessingFilter
 * пропускать запросы с basic auth дальше по цепочке фильтров,
 * а также отдавать приоритет авторизации с помощью jwt.
 */
public class IgnoreBasicAuthRequestHeaderRequestMatcher implements RequestMatcher {
  private static final String BASIC_AUTH = "Basic ";
  private static final String BEARER_AUTH = "Bearer ";

  @Override
  public boolean matches(HttpServletRequest request) {
    Enumeration<String> authorizationHeaders = request.getHeaders(HttpHeaders.AUTHORIZATION);
    if (!authorizationHeaders.hasMoreElements()) {
      return false;
    }

    boolean hasBasicAuth = false;
    while (authorizationHeaders.hasMoreElements()) {
      String header = authorizationHeaders.nextElement();
      if (header.startsWith(BEARER_AUTH)) {
        return true;
      } else if (header.startsWith(BASIC_AUTH)) {
        hasBasicAuth = true;
      }
    }

    return !hasBasicAuth;
  }
}
