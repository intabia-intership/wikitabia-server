package com.intabia.wikitabia.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * тело ответа при запрете доступа к ресурсу, например из-за
 * {@link org.springframework.security.access.AccessDeniedException AccessDeniedException} или
 * {@link org.springframework.security.core.AuthenticationException AuthenticationException}.
 */
@Getter
@Setter
@NoArgsConstructor
public class AccessDeniedResponse extends ExceptionResponse {
  private String requestUri;
}
