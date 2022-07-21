package com.intabia.wikitabia.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * передает исключение на обработку в {@link GlobalExceptionHandler GlobalExceptionHandler}.
 */
@Component
public class DelegatingKeycloakAuthenticationEntryPoint extends KeycloakAuthenticationEntryPoint {
  private HandlerExceptionResolver resolver;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  public void setResolver(HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  public DelegatingKeycloakAuthenticationEntryPoint(
      AdapterDeploymentContext adapterDeploymentContext) {
    super(adapterDeploymentContext);
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException exception) {
    resolver.resolveException(request, response, null, exception);
  }
}
