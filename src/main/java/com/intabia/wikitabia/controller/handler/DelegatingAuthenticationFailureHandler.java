package com.intabia.wikitabia.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * передает исключение на обработку в {@link GlobalExceptionHandler GlobalExceptionHandler}.
 */
@Component
public class DelegatingAuthenticationFailureHandler implements AuthenticationFailureHandler {
  private HandlerExceptionResolver resolver;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  public void setResolver(HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) {
    resolver.resolveException(request, response, null, exception);
  }
}
