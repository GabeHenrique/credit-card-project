package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.ProductDto;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public ProductDto createProduct(ProductDto productDto) {
    var product = productDto.toEntity();
    product.setId(UUID.randomUUID().toString());
    return ProductDto.fromEntity(productRepository.save(product));
  }

  public ProductDto findProductById(String id) {
    return ProductDto.fromEntity(
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product.not.found")));
  }

  public ProductDto updateProduct(String id, ProductDto productDto) {
    var oldProduct =
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product.not.found"));
    var newProduct = productDto.toEntity();
    newProduct.setId(oldProduct.getId());
    return ProductDto.fromEntity(productRepository.save(newProduct));
  }

  public void deleteProduct(String id) {
    var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product.not.found"));
    productRepository.delete(product);
  }

  public List<ProductDto> findAllProducts() {
    return productRepository.findAll().stream().map(ProductDto::fromEntity).toList();
  }
}
