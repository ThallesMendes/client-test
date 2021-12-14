package br.com.valerianosoft.client.config;

import static java.text.MessageFormat.format;

import br.com.valerianosoft.client.common.exception.EntityNotFoundException;
import br.com.valerianosoft.client.common.response.ResponseError;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class RestControllerAdvice {

  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ResponseError entityNotFound(final EntityNotFoundException ex) {

    log.error(ex.getMessage(), ex);

    return ResponseError.builder()
        .message(ex.getMessage())
        .build();
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseError constraintViolationException(final ConstraintViolationException ex) {
    log.error(ex.getMessage(), ex);

    return ResponseError.builder()
        .message(ex.getMessage())
        .build();
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseError methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);

    var currentError = "Unknown error validation";

    if (Objects.nonNull(ex.getFieldError())) {
      final var error = ex.getFieldError();
      currentError = format("{0} - {1}", error.getField(), error.getDefaultMessage());
    }

    return ResponseError.builder()
        .message(currentError)
        .build();
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseError exceptionGeneric(final Exception ex) {

    final var message = format("Unknown error ! Class: {0} Message: {1}",
        ex.getClass().getSimpleName(),
        ex.getMessage());

    log.error(message, ex);

    return ResponseError.builder()
        .message(message)
        .build();
  }

}
