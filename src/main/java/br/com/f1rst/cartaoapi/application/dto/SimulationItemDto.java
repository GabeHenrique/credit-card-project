package br.com.f1rst.cartaoapi.application.dto;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationItemDto {

  private String cardName;
  private String[] benefits;
  private BigDecimal annualFee;
  private BigDecimal installmentValue;
  private Integer installments;
  private CardTier tier;

}
