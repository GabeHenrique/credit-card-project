package br.com.f1rst.cartaoapi.domain.model;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

  @Id
  private String id;
  private String name;
  private String[] benefits;
  private BigDecimal annualFee;
  private Integer installments;
  private CardTier tier;

}
