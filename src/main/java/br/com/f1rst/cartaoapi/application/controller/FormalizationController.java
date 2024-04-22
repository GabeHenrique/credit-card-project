package br.com.f1rst.cartaoapi.application.controller;

import br.com.f1rst.cartaoapi.application.dto.ConfirmationDto;
import br.com.f1rst.cartaoapi.application.dto.FormalizationDto;
import br.com.f1rst.cartaoapi.application.dto.SimulationDto;
import br.com.f1rst.cartaoapi.domain.service.FormalizationService;
import br.com.f1rst.cartaoapi.domain.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Formalizar Contratação")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contracting/formalization")
public class FormalizationController {

  private final FormalizationService formalizationService;

  @Operation(
      summary = "Formalizar contratação",
      description = "Formaliza a contratação de um cartão de crédito, passando os dados necessários para a contratação"
  )
  @GetMapping(value = "/simulate", produces = "application/json")
  public ConfirmationDto simulateContracting(@RequestBody FormalizationDto formalizationDto) {
    return formalizationService.formalizeContracting(formalizationDto);
  }
}
