package br.com.f1rst.cartaoapi.application.controller;

import br.com.f1rst.cartaoapi.application.dto.SimulationDto;
import br.com.f1rst.cartaoapi.domain.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Simular Contratação")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contracting/simulation")
public class SimulationController {

  private final SimulationService simulationService;

  @Operation(
      summary = "Simular contratação",
      description = "Simula a contratação de um cartão de crédito."
  )

  @GetMapping(value = "/simulate", produces = "application/json")
  public SimulationDto simulateContracting(@RequestParam String customerCpf) {
    return simulationService.simulateContracting(customerCpf);
  }
}
