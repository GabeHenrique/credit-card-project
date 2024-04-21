package br.com.f1rst.cartaoapi.infrastructure.exception.handler;

import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceAlreadyExistsException;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import br.com.f1rst.cartaoapi.infrastructure.exception.message.ErrorMessage;
import br.com.f1rst.cartaoapi.infrastructure.exception.message.ErrorMessage.ErrorDetail;
import br.com.f1rst.cartaoapi.infrastructure.exception.message.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    String code = "validation.error";
    var error = ErrorMessage.builder()
        .code(code)
        .message(resolver.translate(code))
        .details(getValidationErrorFields(ex.getBindingResult()))
        .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        .build();
    return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY,
        request);
  }

  private List<ErrorDetail> getValidationErrorFields(BindingResult bindingResult) {
    var errors = bindingResult.getFieldErrors().stream()
        .map(e -> ErrorDetail.builder()
            .field(e.getField())
            .code(e.getDefaultMessage())
            .message(resolver.translate(e.getDefaultMessage()))
            .build())
        .toList();

    if (errors.isEmpty()) {
      errors = bindingResult.getAllErrors().stream()
          .map(e -> ErrorDetail.builder()
              .fields(Arrays.stream((String[]) Objects.requireNonNull(e.getArguments())[1]).toList())
              .code(e.getDefaultMessage())
              .message(resolver.translate(e.getDefaultMessage()))
              .build())
          .toList();
    }
    return errors;
  }
}

