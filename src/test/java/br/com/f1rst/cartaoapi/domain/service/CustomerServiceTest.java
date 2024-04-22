package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.CustomerDto;
import br.com.f1rst.cartaoapi.domain.model.Customer;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceAlreadyExistsException;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;
  @InjectMocks
  private CustomerService customerService;

  private static final String CUSTOMER_ID = UUID.randomUUID().toString();
  private static final String CPF_CNPJ = "78825836007";
  private static final CustomerDto CUSTOMER_DTO = new CustomerDto(null, "John Doe", CPF_CNPJ, "email@email.com");

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    CUSTOMER_DTO.setCpfCnpj(CPF_CNPJ);
  }

  @Test
  void testCreateCustomer() {
    when(customerRepository.existsByCpfCnpj(CPF_CNPJ)).thenReturn(false);
    var savedCustomer = CUSTOMER_DTO.toEntity();
    savedCustomer.setId(CUSTOMER_ID);
    when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

    var createdCustomerDto = customerService.createCustomer(CUSTOMER_DTO);

    assertNotNull(createdCustomerDto);
    assertNotNull(createdCustomerDto.getId());
  }

  @Test
  void testCreateCustomer_InvalidCpfCnpj() {
    when(customerRepository.existsByCpfCnpj(CPF_CNPJ)).thenReturn(false);
    CUSTOMER_DTO.setCpfCnpj("1234567890");
    assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(CUSTOMER_DTO));
  }

  @Test
  void testCreateCustomer_AlreadyExists() {
    when(customerRepository.existsByCpfCnpj(CPF_CNPJ)).thenReturn(true);
    assertThrows(ResourceAlreadyExistsException.class, () -> customerService.createCustomer(CUSTOMER_DTO));
  }

  @Test
  void testFindCustomerById() {
    var customer = CUSTOMER_DTO.toEntity();
    customer.setId(CUSTOMER_ID);
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    var foundCustomerDto = customerService.findCustomerById(CUSTOMER_ID);

    assertNotNull(foundCustomerDto);
    assertEquals(CUSTOMER_ID, foundCustomerDto.getId());
    assertEquals(CPF_CNPJ, foundCustomerDto.getCpfCnpj());
  }

  @Test
  void testFindCustomerById_NotFound() {
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(CUSTOMER_ID));
  }

  @Test
  void testUpdateCustomer() {
    var newCustomer = CUSTOMER_DTO.toEntity();
    newCustomer.setId(CUSTOMER_ID);
    when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

    var oldCustomer = new Customer();
    oldCustomer.setId(CUSTOMER_ID);
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(oldCustomer));

    var updatedCustomerDto = customerService.updateCustomer(CUSTOMER_ID, CUSTOMER_DTO);

    assertNotNull(updatedCustomerDto);
    assertEquals(CUSTOMER_ID, updatedCustomerDto.getId());
    assertEquals(CPF_CNPJ, updatedCustomerDto.getCpfCnpj());
  }

  @Test
  void testUpdateCustomer_NotFound() {
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(CUSTOMER_ID, CUSTOMER_DTO));
  }

  @Test
  void testDeleteCustomer() {
    var customer = new Customer();
    customer.setId(CUSTOMER_ID);
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    customerService.deleteCustomer(CUSTOMER_ID);
    verify(customerRepository, times(1)).delete(customer);
  }

  @Test
  void testDeleteCustomer_NotFound() {
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(CUSTOMER_ID));
  }
}