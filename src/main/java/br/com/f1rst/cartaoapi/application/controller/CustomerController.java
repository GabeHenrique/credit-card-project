package br.com.f1rst.cartaoapi.application.controller;

import br.com.f1rst.cartaoapi.application.dto.CustomerDto;
import br.com.f1rst.cartaoapi.domain.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Registrar Clientes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  @Operation(
      summary = "Criar um cliente",
      description = "Cria um novo cliente na base de dados caso o CPF/CNPJ ainda não exista."
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDto create(@RequestBody @Valid CustomerDto dto) {
    return customerService.createCustomer(dto);
  }

  @Operation(
      summary = "Buscar um cliente",
      description = "Busca um cliente na base de dados pelo ID."
  )
  @GetMapping(value = "/find/{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDto findCustomerById(@PathVariable String id) {
    return customerService.findCustomerById(id);
  }

  @Operation(
      summary = "Atualizar um cliente",
      description = "Atualiza um cliente na base de dados pelo ID."
  )
  @PutMapping(value = "/edit/{id}", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CustomerDto updateCustomer(@PathVariable String id, @RequestBody @Valid CustomerDto dto) {
    return customerService.updateCustomer(id, dto);
  }

  @Operation(
      summary = "Deletar um cliente",
      description = "Deleta um cliente na base de dados pelo ID."
  )
  @DeleteMapping(value = "/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable String id) {
    customerService.deleteCustomer(id);
  }
}
