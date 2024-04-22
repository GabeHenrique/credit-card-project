package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.CustomerDto;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceAlreadyExistsException;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.f1rst.cartaoapi.infrastructure.utils.DocumentsUtils.isValidCpfCnpj;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerDto createCustomer(CustomerDto customerDto) {
    if (customerRepository.existsByCpfCnpj(customerDto.getCpfCnpj())) {
      throw new ResourceAlreadyExistsException("cpf.cnpj.already.registered");
    }
    isValidCpfCnpj(customerDto.getCpfCnpj());
    var customer = customerDto.toEntity();
    customer.setId(UUID.randomUUID().toString());
    return CustomerDto.fromEntity(customerRepository.save(customer));
  }

  public CustomerDto findCustomerById(String id) {
    return customerRepository.findById(id).map(CustomerDto::fromEntity).orElseThrow(
        () -> new ResourceNotFoundException("customer.not.found"));
  }

  public CustomerDto updateCustomer(String id, CustomerDto customerDto) {
    var oldCustomer =
        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer.not.found"));
    isValidCpfCnpj(customerDto.getCpfCnpj());
    var newCustomer = customerDto.toEntity();
    newCustomer.setId(oldCustomer.getId());
    return CustomerDto.fromEntity(customerRepository.save(newCustomer));
  }

  public void deleteCustomer(String id) {
    var cliente =
        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer.not.found"));
    customerRepository.delete(cliente);
  }
}
