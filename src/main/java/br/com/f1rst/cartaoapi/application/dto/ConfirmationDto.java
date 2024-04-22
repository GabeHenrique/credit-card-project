package br.com.f1rst.cartaoapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationDto {

  private boolean success;
  private String message;

}
