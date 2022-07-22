package com.intabia.wikitabia.mappers.exception;

import com.intabia.wikitabia.exception.BadRequestException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.response.BadRequestResponse;
import com.intabia.wikitabia.exception.response.EntityNotFoundResponse;
import com.intabia.wikitabia.exception.response.ExceptionResponse;
import org.mapstruct.Mapper;

/**
 * маппер для исключений.
 */
@Mapper(componentModel = "spring")
public interface ExceptionMapper {
  EntityNotFoundResponse entityNotFoundExceptionToResponse(EntityNotFoundException e);

  BadRequestResponse badRequestExceptionToResponse(BadRequestException e);

  ExceptionResponse runtimeExceptionToResponse(RuntimeException e, String userFriendlyMessage);
}
