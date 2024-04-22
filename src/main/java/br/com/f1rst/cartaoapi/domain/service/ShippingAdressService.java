package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.ShippingAdressDto;
import br.com.f1rst.cartaoapi.domain.repository.ShippingAdressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingAdressService {

  private final ShippingAdressRepository shippingAdressRepository;

  public void saveShippingAdress(ShippingAdressDto shippingAdressDto, String customerId) {
    var address = shippingAdressDto.toEntity(customerId);
    address.setId(UUID.randomUUID().toString());
    shippingAdressRepository.save(address);
  }
}
