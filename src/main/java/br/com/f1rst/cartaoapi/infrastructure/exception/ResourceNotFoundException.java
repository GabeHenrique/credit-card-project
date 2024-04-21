package br.com.f1rst.cartaoapi.infrastructure.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class ResourceNotFoundException extends EmptyResultDataAccessException {

  public ResourceNotFoundException() {
    this("resource.not.found");
  }

  public ResourceNotFoundException(String msg) {
    super(msg, 1);
  }

}
