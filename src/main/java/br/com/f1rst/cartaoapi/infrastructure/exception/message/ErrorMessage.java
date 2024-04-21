package br.com.f1rst.cartaoapi.infrastructure.exception.message;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

  private Integer status;
  private String code;
  private String message;
  private HttpStatus httpStatus;

}
