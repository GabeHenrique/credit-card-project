package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.domain.enums.CardTier;
import br.com.f1rst.cartaoapi.domain.model.CreditCard;
import br.com.f1rst.cartaoapi.domain.model.Customer;
import br.com.f1rst.cartaoapi.domain.model.Product;
import br.com.f1rst.cartaoapi.domain.repository.CreditCardRepository;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardServiceTest {

  @Mock
  private CreditCardRepository creditCardRepository;
  @Mock
  private ProductRepository productRepository;
  @InjectMocks
  private CreditCardService creditCardService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private static final String PRODUCT_ID = UUID.randomUUID().toString();
  private static final String CUSTOMER_ID = UUID.randomUUID().toString();
  private static final CardTier CARD_TIER = CardTier.GOLD;

  @Test
  void testCreateCreditCard() {
    var customer = new Customer();
    customer.setId(CUSTOMER_ID);
    var product = new Product();
    product.setTier(CARD_TIER);
    when(productRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.of(product));

    creditCardService.createCreditCard(customer, PRODUCT_ID);

    verify(creditCardRepository, times(1)).save(any(CreditCard.class));
  }

  @Test
  void testGenerateCreditCardData() {
    var customer = new Customer();
    customer.setId(CUSTOMER_ID);
    var product = new Product();
    product.setTier(CARD_TIER);

    var creditCard = creditCardService.generateCreditCardData(customer, product);

    assertNotNull(creditCard);
    assertEquals(CUSTOMER_ID, creditCard.getCustomerId());
    assertEquals(16, creditCard.getNumber().length());
    assertTrue(creditCard.getExpirationDate().matches("\\d{2}/\\d{4}"));
    assertEquals(3, creditCard.getCvv().length());
    assertEquals(CARD_TIER, creditCard.getCardTier());
    assertNotNull(creditCard.getId());
  }

  @Test
  void testGenerateCardNumber() {
    var cardNumber = CreditCardService.generateCardNumber();

    assertNotNull(cardNumber);
    assertEquals(16, cardNumber.length());
  }

  @Test
  void testGenerateCVV() {
    var cvv = CreditCardService.generateCVV();

    assertNotNull(cvv);
    assertEquals(3, cvv.length());
  }

  @Test
  void testGenerateExpirationDate() {
    var expirationDate = CreditCardService.generateExpirationDate();

    assertNotNull(expirationDate);
    assertTrue(expirationDate.matches("\\d{2}/\\d{4}"));
  }

}