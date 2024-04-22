package br.com.f1rst.cartaoapi.infrastructure.exception;

import lombok.Getter;

@Getter
public class PreconditionException extends RuntimeException {

  private final transient Object[] args;

  public PreconditionException(String message, Object... args) {
    super(message);
    this.args = args;
  }

  public PreconditionException(String message, Exception exception, Object... args) {
    super(message, exception);
    this.args = args;
  }
}
