package br.com.f1rst.cartaoapi.domain.repository;

import br.com.f1rst.cartaoapi.domain.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

  boolean existsByCpfCnpj(String cpfCnpj);

  Optional<Customer> findByCpfCnpj(String cpfCnpj);

}
