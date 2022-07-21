package com.intabia.wikitabia.mappers.exception;

import com.intabia.wikitabia.exception.BadRequestException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.response.AccessDeniedResponse;
import com.intabia.wikitabia.exception.response.BadRequestResponse;
import com.intabia.wikitabia.exception.response.EntityNotFoundResponse;
import com.intabia.wikitabia.exception.response.ExceptionResponse;
import org.mapstruct.Mapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * маппер для исключений.
 */
@Mapper(componentModel = "spring")
public interface ExceptionMapper {
  EntityNotFoundResponse entityNotFoundExceptionToResponse(EntityNotFoundException e);

  BadRequestResponse badRequestExceptionToResponse(BadRequestException e);

  ExceptionResponse runtimeExceptionToResponse(RuntimeException e, String userFriendlyMessage);

  AccessDeniedResponse accessDeniedExceptionToResponse(AccessDeniedException e,
                                                       String userFriendlyMessage,
                                                       String requestUri);

  AccessDeniedResponse authenticationExceptionToResponse(AuthenticationException e,
                                                         String userFriendlyMessage,
                                                         String requestUri);
}
