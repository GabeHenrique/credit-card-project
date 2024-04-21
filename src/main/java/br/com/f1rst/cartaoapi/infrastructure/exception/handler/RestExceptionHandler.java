package br.com.f1rst.cartaoapi.infrastructure.exception.handler;

import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceAlreadyExistsException;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import br.com.f1rst.cartaoapi.infrastructure.exception.message.ErrorMessage;
import br.com.f1rst.cartaoapi.infrastructure.exception.message.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageResolver resolver;

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
    log.error("Resource not found", exception);
    var httpStatus = HttpStatus.NOT_FOUND;
    return handleExceptionInternal(exception, ErrorMessage.builder()
            .status(httpStatus.value())
            .code(exception.getMessage())
            .httpStatus(httpStatus)
            .message(resolver.translate(exception.getMessage()))
            .build(),
        new HttpHeaders(),
        HttpStatus.NOT_FOUND,
        request);
  }

  @ExceptionHandler({ResourceAlreadyExistsException.class})
  public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception, WebRequest request) {
    log.error("Resource already exists", exception);
    var httpStatus = HttpStatus.CONFLICT;
    return handleExceptionInternal(
        exception,
        ErrorMessage.builder()
            .status(httpStatus.value())
            .code(exception.getMessage())
            .httpStatus(httpStatus)
            .message(resolver.translate(exception.getMessage()))
            .build(),
        new HttpHeaders(),
        HttpStatus.CONFLICT,
        request);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
    log.error("Illegal argument", exception);
    var httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(
        exception,
        ErrorMessage.builder()
            .status(httpStatus.value())
            .code(exception.getMessage())
            .httpStatus(httpStatus)
            .message(resolver.translate(exception.getMessage()))
            .build(),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }
}
