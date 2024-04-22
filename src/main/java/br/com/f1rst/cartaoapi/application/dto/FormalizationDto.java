package br.com.f1rst.cartaoapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormalizationDto {

  private String customerCpf;
  private String productId;
  private ShippingAdressDto shippingAdress;

}
