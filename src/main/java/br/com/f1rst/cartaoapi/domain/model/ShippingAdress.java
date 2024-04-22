package br.com.f1rst.cartaoapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shippingAdresses")
public class ShippingAdress {

  @Id
  private String id;
  private String street;
  private String number;
  private String complement;
  private String neighborhood;
  private String city;
  private String state;
  private String zipCode;
  private String customerId;
}
