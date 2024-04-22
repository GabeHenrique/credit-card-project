package br.com.f1rst.cartaoapi.domain.service;

import br.com.f1rst.cartaoapi.application.dto.ProductDto;
import br.com.f1rst.cartaoapi.domain.model.Product;
import br.com.f1rst.cartaoapi.domain.repository.ProductRepository;
import br.com.f1rst.cartaoapi.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;
  @InjectMocks
  private ProductService productService;

  private static final String PRODUCT_ID = UUID.randomUUID().toString();
  private static final ProductDto PRODUCT_DTO = new ProductDto();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateProduct_Success() {
    var product = new Product();
    when(productRepository.save(any(Product.class))).thenReturn(product);

    var createdProductDto = productService.createProduct(PRODUCT_DTO);

    assertNotNull(createdProductDto);
    assertEquals(product.getId(), createdProductDto.getId());
  }

  @Test
  void testCreateProduct_NullProductDtoFields() {
    when(productRepository.save(any(Product.class))).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> productService.createProduct(PRODUCT_DTO));
  }

  @Test
  void testFindProductById_Success() {
    var product = new Product();
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

    var foundProductDto = productService.findProductById(PRODUCT_ID);

    assertNotNull(foundProductDto);
    assertEquals(product.getId(), foundProductDto.getId());
  }

  @Test
  void testFindProductById_NotFound() {
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productService.findProductById(PRODUCT_ID));
  }

  @Test
  void testUpdateProduct_Success() {
    var oldProduct = new Product();
    oldProduct.setId(PRODUCT_ID);
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(oldProduct));
    when(productRepository.save(any(Product.class))).thenReturn(oldProduct);

    var updatedProductDto = productService.updateProduct(PRODUCT_ID, PRODUCT_DTO);

    assertNotNull(updatedProductDto);
    assertEquals(oldProduct.getId(), updatedProductDto.getId());
  }

  @Test
  void testUpdateProduct_NotFound() {
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(PRODUCT_ID, PRODUCT_DTO));
  }

  @Test
  void testUpdateProduct_NullProductDtoFields() {
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(new Product()));
    assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(PRODUCT_ID, PRODUCT_DTO));
  }

  @Test
  void testDeleteProduct_Success() {
    var product = new Product();
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

    assertDoesNotThrow(() -> productService.deleteProduct(PRODUCT_ID));

    verify(productRepository, times(1)).delete(product);
  }

  @Test
  void testDeleteProduct_NotFound() {
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(PRODUCT_ID));
  }

  @Test
  void testFindAllProducts_Success() {
    var productList = new ArrayList<Product>();
    var product1 = new Product();
    product1.setId(UUID.randomUUID().toString());
    var product2 = new Product();
    product2.setId(UUID.randomUUID().toString());
    productList.add(product1);
    productList.add(product2);
    when(productRepository.findAll()).thenReturn(productList);

    var productDtoList = productService.findAllProducts();

    assertNotNull(productDtoList);
    assertEquals(2, productDtoList.size());
  }
}