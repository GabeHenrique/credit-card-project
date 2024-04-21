package br.com.f1rst.cartaoapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationDto {

  private String customerName;
  private String customerCpf;
  private String customerEmail;
  private List<SimulationItemDto> items;

  public SimulationDto toSimulationDto(CustomerDto customer, List<ProductDto> products) {
    var simulationDto = new SimulationDto();
    simulationDto.setCustomerName(customer.getName());
    simulationDto.setCustomerCpf(customer.getCpfCnpj());
    simulationDto.setCustomerEmail(customer.getEmail());
    var itens = new ArrayList<SimulationItemDto>();
    products.forEach(product -> {
      var simulationItemDto = new SimulationItemDto();
      simulationItemDto.setCardName(product.getName());
      simulationItemDto.setTier(product.getTier());
      simulationItemDto.setAnnualFee(product.getAnnualFee());
      simulationItemDto.setInstallmentValue(product.getInstallmentValue());
      simulationItemDto.setInstallments(product.getInstallments());
      simulationItemDto.setBenefits(product.getBenefits());
      itens.add(simulationItemDto);
    });
    simulationDto.setItems(itens);
    return simulationDto;
  }

}
