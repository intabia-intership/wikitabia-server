package com.intabia.wikitabia.exception;

import com.intabia.wikitabia.exception.response.DataNotFoundResponse;
import com.intabia.wikitabia.exception.response.ExceptionResponse;
import com.intabia.wikitabia.exception.response.InvalidBodyResponse;
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
public class GlobalExceptionHandler {
  private static final String UNEXPECTED_EXCEPTION_MSG = "Произошла непредвиденная ошибка";

  @ExceptionHandler(value = DataNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public DataNotFoundResponse handleDataNotFoundException(DataNotFoundException ex) {
    return new DataNotFoundResponse(ex.getMessage(), ex.getFriendlyMessage(),
        ex.getId(), ex.getEntity());
  }

  @ExceptionHandler(value = InvalidBodyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public InvalidBodyResponse handleInvalidBodyException(InvalidBodyException ex) {
    return new InvalidBodyResponse(ex.getMessage(), ex.getFriendlyMessage(),
        ex.getPropertyChain(), ex.getActualValue(), ex.getRule());
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ExceptionResponse handleUnexpectedException(RuntimeException ex) {
    return new ExceptionResponse(ex.getMessage(), UNEXPECTED_EXCEPTION_MSG);
  }
}
