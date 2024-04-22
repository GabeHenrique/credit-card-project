package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.domain.model.Customer;
import br.com.f1rst.cartaoapi.domain.model.Product;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SimulationServiceTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private CustomerRepository customerRepository;
  @InjectMocks
  private SimulationService contractService;

  private static final String CUSTOMER_CPF = "12345678901";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSimulateContracting_Success() {
    var productList = new ArrayList<Product>();
    var product1 = new Product();
    product1.setId(UUID.randomUUID().toString());
    var product2 = new Product();
    product2.setId(UUID.randomUUID().toString());
    productList.add(product1);
    productList.add(product2);
    when(productRepository.findAll()).thenReturn(productList);

    var customer = new Customer();
    when(customerRepository.findByCpfCnpj(CUSTOMER_CPF)).thenReturn(Optional.of(customer));

    var simulationDto = contractService.simulateContracting(CUSTOMER_CPF);

    assertNotNull(simulationDto);
  }

  @Test
  void testSimulateContracting_NoProducts() {
    when(productRepository.findAll()).thenReturn(new ArrayList<>());
    assertThrows(ResourceNotFoundException.class, () -> contractService.simulateContracting(CUSTOMER_CPF));
  }

  @Test
  void testSimulateContracting_CustomerNotFound() {
    when(productRepository.findAll()).thenReturn(new ArrayList<>());
    when(customerRepository.findByCpfCnpj(CUSTOMER_CPF)).thenReturn(java.util.Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> contractService.simulateContracting(CUSTOMER_CPF));
  }

}