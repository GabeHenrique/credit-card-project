package br.com.f1rst.cartaoapi.application.dto;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import br.com.f1rst.cartaoapi.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String id;
  @NotNull
  private String name;
  @NotNull
  private String[] benefits;
  @NotNull
  private BigDecimal annualFee;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private BigDecimal installmentValue;
  @NotNull
  private Integer installments;
  @NotNull
  private CardTier tier;

  public Product toEntity() {
    return Product.builder()
        .id(id)
        .name(name)
        .benefits(benefits)
        .annualFee(annualFee)
        .installments(installments)
        .tier(tier)
        .build();
  }

  public static ProductDto fromEntity(Product product) {
    var productDto = new ProductDto();
    BeanUtils.copyProperties(product, productDto);
    return productDto;
  }

  public BigDecimal getInstallmentValue() {
    if (installments != null && annualFee != null && installments > 0 && annualFee.compareTo(BigDecimal.ZERO) > 0) {
      return annualFee.divide(BigDecimal.valueOf(installments), 2, HALF_EVEN);
    } else {
      return BigDecimal.ZERO;
    }
  }
}
