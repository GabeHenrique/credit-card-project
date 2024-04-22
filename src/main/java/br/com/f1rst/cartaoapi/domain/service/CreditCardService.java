package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.domain.model.CreditCard;
import br.com.f1rst.cartaoapi.domain.model.Customer;
import br.com.f1rst.cartaoapi.domain.model.Product;
import br.com.f1rst.cartaoapi.domain.repository.CreditCardRepository;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardService {

  private final CreditCardRepository creditCardRepository;
  private final ProductRepository productRepository;

  private static final String EXPIRATION_DATE_FORMAT = "MM/yyyy";
  private static final Random random = new Random();

  public void createCreditCard(Customer customer, String productId) {
    var product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("product.not.found"));

    var creditCard = generateCreditCardData(customer, product);
    creditCardRepository.save(creditCard);
  }

  public CreditCard generateCreditCardData(Customer customer, Product product) {
    var cardNumber = generateCardNumber();
    var cvv = generateCVV();
    var expirationDate = generateExpirationDate();
    return CreditCard.builder()
        .customerId(customer.getId())
        .number(cardNumber)
        .expirationDate(expirationDate)
        .cvv(cvv)
        .id(UUID.randomUUID().toString())
        .cardTier(product.getTier())
        .build();
  }

  public static String generateCardNumber() {
    long cardNumber = Math.abs(random.nextLong() % 1_000_000_000_000_000L);
    return String.format("%016d", cardNumber);
  }

  public static String generateCVV() {
    int cvv = Math.abs(random.nextInt() % 1000);
    return String.format("%03d", cvv);
  }

  public static String generateExpirationDate() {
    LocalDate currentDate = LocalDate.now();
    LocalDate expirationDate = currentDate.plusYears(random.nextInt(10)).plusMonths(random.nextInt(12));
    return expirationDate.format(DateTimeFormatter.ofPattern(EXPIRATION_DATE_FORMAT));
  }
}
