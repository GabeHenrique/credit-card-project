package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.CustomerDto;
import br.com.f1rst.cartaoapi.application.dto.ProductDto;
import br.com.f1rst.cartaoapi.application.dto.SimulationDto;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimulationService {

  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;

  public SimulationDto simulateContracting(String customerCpf) {
    var products = productRepository.findAll().stream().map(ProductDto::fromEntity).toList();
    if (products.isEmpty()) {
      throw new ResourceNotFoundException("there.are.no.products.to.simulate");
    }
    var customer = customerRepository.findByCpfCnpj(customerCpf).map(CustomerDto::fromEntity).orElseThrow(
        () -> new ResourceNotFoundException("customer.not.found"));
    return new SimulationDto().toSimulationDto(customer, products);
  }
}
