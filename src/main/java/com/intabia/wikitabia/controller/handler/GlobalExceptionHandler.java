package com.intabia.wikitabia.controller.handler;

import com.intabia.wikitabia.exception.BadRequestException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.response.BadRequestResponse;
import com.intabia.wikitabia.exception.response.EntityNotFoundResponse;
import com.intabia.wikitabia.exception.response.ExceptionResponse;
import com.intabia.wikitabia.mappers.exception.ExceptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j(topic = "com.intabia.wikitabia.logger")
public class GlobalExceptionHandler {
  private final ExceptionMapper exceptionMapper;
  private static final String UNEXPECTED_EXCEPTION_MSG = "Произошла непредвиденная ошибка";

  @ExceptionHandler(value = EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public EntityNotFoundResponse handleDataNotFoundException(EntityNotFoundException ex) {
    log.warn("Поймано исключение:", ex);
    return exceptionMapper.entityNotFoundExceptionToResponse(ex);
  }

  @ExceptionHandler(value = BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public BadRequestResponse handleBadRequestException(BadRequestException ex) {
    log.warn("Поймано исключение:", ex);
    return exceptionMapper.badRequestExceptionToResponse(ex);
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ExceptionResponse handleUnexpectedException(RuntimeException ex) {
    log.warn("Поймано исключение:", ex);
    return exceptionMapper.runtimeExceptionToResponse(ex, UNEXPECTED_EXCEPTION_MSG);
  }
}
