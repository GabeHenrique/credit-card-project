package br.com.f1rst.cartaoapi.domain.repository;

import br.com.f1rst.cartaoapi.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
