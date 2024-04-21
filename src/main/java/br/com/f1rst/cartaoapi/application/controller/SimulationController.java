package br.com.f1rst.cartaoapi.application.controller;

import br.com.f1rst.cartaoapi.application.dto.SimulationDto;
import br.com.f1rst.cartaoapi.domain.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Simular Contratação")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contracting/simulation")
public class SimulationController {

  private final SimulationService simulationService;

  @Operation(
      summary = "Simular contratação",
      description = "Simula a contratação de um cartão de crédito, passando como parâmetro o CPF do cliente"
  )
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/simulate", produces = "application/json")
  public SimulationDto simulateContracting(@RequestParam String customerCpf) {
    return simulationService.simulateContracting(customerCpf);
  }
}
