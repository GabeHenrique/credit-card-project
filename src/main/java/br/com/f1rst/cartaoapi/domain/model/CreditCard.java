package br.com.f1rst.cartaoapi.domain.model;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditCards")
public class CreditCard {

  private String id;
  private String number;
  private String expirationDate;
  private String cvv;
  private String customerId;
  private CardTier cardTier;
}
