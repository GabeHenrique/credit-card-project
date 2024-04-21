package br.com.f1rst.cartaoapi.infrastructure.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

  public ResourceAlreadyExistsException() {
    this("resource.already.exists");
  }

  public ResourceAlreadyExistsException(String msg) {
    super(msg);
  }

}
