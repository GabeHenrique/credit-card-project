package br.com.f1rst.cartaoapi.application.dto;

import br.com.f1rst.cartaoapi.domain.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  String id;
  @NotNull
  String name;
  @NotNull
  String cpfCnpj;
  @NotNull
  String email;

  public Customer toEntity() {
    return Customer.builder()
        .id(id)
        .name(name)
        .cpfCnpj(cpfCnpj)
        .email(email)
        .build();
  }

  public static CustomerDto fromEntity(Customer customer) {
    var clienteDto = new CustomerDto();
    BeanUtils.copyProperties(customer, clienteDto);
    return clienteDto;
  }
}
