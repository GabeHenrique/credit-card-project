package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.ShippingAdressDto;
import br.com.f1rst.cartaoapi.domain.model.ShippingAdress;
import br.com.f1rst.cartaoapi.domain.repository.ShippingAdressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShippingAdressServiceTest {

  @Mock
  private ShippingAdressRepository shippingAdressRepository;
  @InjectMocks
  private ShippingAdressService shippingAdressService;

  private static final String CUSTOMER_ID = UUID.randomUUID().toString();
  private static final ShippingAdressDto SHIPPING_ADRESS_DTO = new ShippingAdressDto();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveShippingAdress_Success() {
    var shippingAdress = new ShippingAdress();
    when(shippingAdressRepository.save(any(ShippingAdress.class))).thenReturn(shippingAdress);

    shippingAdressService.saveShippingAdress(SHIPPING_ADRESS_DTO, CUSTOMER_ID);
    verify(shippingAdressRepository, times(1)).save(any(ShippingAdress.class));
  }

  @Test
  void testSaveShippingAdress_NullShippingAdressDtoFields() {
    when(shippingAdressRepository.save(any(ShippingAdress.class))).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class,
        () -> shippingAdressService.saveShippingAdress(SHIPPING_ADRESS_DTO, CUSTOMER_ID));
  }
}