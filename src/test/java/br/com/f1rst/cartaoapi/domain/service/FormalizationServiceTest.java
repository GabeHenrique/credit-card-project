package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.FormalizationDto;
import br.com.f1rst.cartaoapi.domain.model.Customer;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FormalizationServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private ShippingAdressService shippingAdressService;

  @Mock
  private CreditCardService creditCardService;

  @InjectMocks
  private FormalizationService contractService;

  private static final String CUSTOMER_CPF = "12345678901";
  private static final String PRODUCT_ID = "product-id";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFormalizeContracting_Success() {
    var customer = new Customer();
    var formalizationDto = new FormalizationDto();
    formalizationDto.setCustomerCpf(CUSTOMER_CPF);
    formalizationDto.setProductId(PRODUCT_ID);
    when(customerRepository.findByCpfCnpj(CUSTOMER_CPF)).thenReturn(Optional.of(customer));

    var confirmationDto = contractService.formalizeContracting(formalizationDto);

    assertNotNull(confirmationDto);
    assertTrue(confirmationDto.isSuccess());
    assertEquals("Parabéns! Seu contrato foi formalizado com sucesso! Em breve você receberá o seu cartão de crédito!",
        confirmationDto.getMessage());
  }

  @Test
  void testFormalizeContracting_CustomerNotFound() {
    var formalizationDto = new FormalizationDto();
    formalizationDto.setCustomerCpf(CUSTOMER_CPF);
    when(customerRepository.findByCpfCnpj(CUSTOMER_CPF)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> contractService.formalizeContracting(formalizationDto));
  }

  @Test
  void testFormalizeContracting_OtherException() {
    var formalizationDto = new FormalizationDto();
    formalizationDto.setCustomerCpf(CUSTOMER_CPF);
    when(customerRepository.findByCpfCnpj(CUSTOMER_CPF)).thenThrow(new RuntimeException());

    var confirmationDto = contractService.formalizeContracting(formalizationDto);

    assertNotNull(confirmationDto);
    assertFalse(confirmationDto.isSuccess());
    assertEquals("Ocorreu um erro ao formalizar o contrato. Por favor, tente novamente.", confirmationDto.getMessage());
  }
}