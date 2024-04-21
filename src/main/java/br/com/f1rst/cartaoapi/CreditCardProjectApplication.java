package br.com.f1rst.cartaoapi;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import br.com.f1rst.cartaoapi.domain.model.Product;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CreditCardProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(CreditCardProjectApplication.class, args);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> ready(ProductRepository productRepository) {
    return event -> {
      if (productRepository.count() == 0) {
        productRepository.saveAll(createProductList());
      }
    };
  }

  private List<Product> createProductList() {
    var list = new ArrayList<Product>();

    list.add(new Product(UUID.randomUUID().toString(), "Standard Card",
        new String[]{"Benefício 1 - Standard", "Benefício 2 - Standard"},
        BigDecimal.valueOf(50), 12, CardTier.STANDARD));

    list.add(
        new Product(UUID.randomUUID().toString(), "Gold Card", new String[]{"Benefício 1 - Gold", "Benefício 2 - Gold"},
            BigDecimal.valueOf(100), 12, CardTier.GOLD));

    list.add(new Product(UUID.randomUUID().toString(), "Platinum Card",
        new String[]{"Benefício 1 - Platinum", "Benefício 2 - Platinum"},
        BigDecimal.valueOf(200), 12, CardTier.PLATINUM));

    list.add(new Product(UUID.randomUUID().toString(), "Black Card",
        new String[]{"Benefício 1 - Black", "Benefício 2 - Black"},
        BigDecimal.valueOf(500), 12, CardTier.BLACK));

    return list;
  }

}
