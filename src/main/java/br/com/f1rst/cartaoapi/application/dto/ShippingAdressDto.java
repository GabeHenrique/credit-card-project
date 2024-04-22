package br.com.f1rst.cartaoapi.application.dto;

import br.com.f1rst.cartaoapi.domain.model.ShippingAdress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAdressDto {

  private String street;
  private String number;
  private String complement;
  private String neighborhood;
  private String city;
  private String state;
  private String zipCode;

  public ShippingAdress toEntity(String customerId) {
    return ShippingAdress.builder()
        .street(street)
        .number(number)
        .complement(complement)
        .neighborhood(neighborhood)
        .city(city)
        .state(state)
        .zipCode(zipCode)
        .customerId(customerId)
        .build();
  }
}
