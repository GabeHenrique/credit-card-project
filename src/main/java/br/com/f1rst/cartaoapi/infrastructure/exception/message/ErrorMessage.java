package br.com.f1rst.cartaoapi.infrastructure.exception.message;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

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
  @Singular
  private List<ErrorDetail> details;

  @Value
  @Builder
  public static class ErrorDetail {
    String field;
    List<String> fields;
    String code;
    String message;
    public String getCode() {
      return StringUtils.capitalize(code);
    }

    public String getMessage() {
      return StringUtils.capitalize(message);
    }

  }
}
