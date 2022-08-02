package com.intabia.wikitabia.controller.handler;

import com.intabia.wikitabia.exception.BadRequestException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.response.BadRequestResponse;
import com.intabia.wikitabia.exception.response.EntityNotFoundResponse;
import com.intabia.wikitabia.exception.response.ExceptionResponse;
import com.intabia.wikitabia.mappers.exception.ExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * обработчик ошибок, который преобразует исключения слоя сервисов
 * в понятные сообщения на слое контроллеров.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger("com.intabia.wikitabia.logger");
  private final ExceptionMapper exceptionMapper;
  private static final String UNEXPECTED_EXCEPTION_MSG = "Произошла непредвиденная ошибка";

  @ExceptionHandler(value = EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public EntityNotFoundResponse handleDataNotFoundException(EntityNotFoundException ex) {
    logger.warn("Поймано исключение:", ex);
    return exceptionMapper.entityNotFoundExceptionToResponse(ex);
  }

  @ExceptionHandler(value = BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public BadRequestResponse handleBadRequestException(BadRequestException ex) {
    logger.warn("Поймано исключение:", ex);
    return exceptionMapper.badRequestExceptionToResponse(ex);
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ExceptionResponse handleUnexpectedException(RuntimeException ex) {
    logger.warn("Поймано исключение:", ex);
    return exceptionMapper.runtimeExceptionToResponse(ex, UNEXPECTED_EXCEPTION_MSG);
  }
}
