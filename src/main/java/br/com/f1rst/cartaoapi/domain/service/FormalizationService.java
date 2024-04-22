package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.ConfirmationDto;
import br.com.f1rst.cartaoapi.application.dto.FormalizationDto;
import br.com.f1rst.cartaoapi.domain.repository.CustomerRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormalizationService {

  private final CustomerRepository customerRepository;
  private final ShippingAdressService shippingAdressService;
  private final CreditCardService creditCardService;

  public ConfirmationDto formalizeContracting(FormalizationDto formalizationDto) {
    try {
      var customer = customerRepository.findByCpfCnpj(formalizationDto.getCustomerCpf())
          .orElseThrow(() -> new ResourceNotFoundException("customer.not.found"));
      shippingAdressService.saveShippingAdress(formalizationDto.getShippingAdress(), customer.getId());
      creditCardService.createCreditCard(customer, formalizationDto.getProductId());
      return new ConfirmationDto(true,
          "Parabéns! Seu contrato foi formalizado com sucesso! Em breve você receberá o seu cartão de crédito!");
    } catch (ResourceNotFoundException e) {
      throw e;
    } catch (Exception e) {
      return new ConfirmationDto(false, "Ocorreu um erro ao formalizar o contrato. Por favor, tente novamente.");
    }
  }
}
