package com.intabia.wikitabia.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * передает исключение на обработку в {@link GlobalExceptionHandler GlobalExceptionHandler}.
 */
@Component
@RequiredArgsConstructor
public class DelegatingAuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Qualifier("handlerExceptionResolver")
  private final HandlerExceptionResolver resolver;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) {
    resolver.resolveException(request, response, null, exception);
  }
}
