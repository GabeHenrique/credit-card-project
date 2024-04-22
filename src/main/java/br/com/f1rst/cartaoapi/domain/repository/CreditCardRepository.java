package br.com.f1rst.cartaoapi.domain.repository;

import br.com.f1rst.cartaoapi.domain.model.CreditCard;
import br.com.f1rst.cartaoapi.domain.model.ShippingAdress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends MongoRepository<CreditCard, String> {

  public boolean existsByNumber(String number);

}
