package br.com.f1rst.cartaoapi.application.controller;

import br.com.f1rst.cartaoapi.application.dto.ProductDto;
import br.com.f1rst.cartaoapi.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registrar Produtos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @Operation(
      summary = "Criar um produto",
      description = "Cria um novo produto na base de dados."
  )
  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto create(@RequestBody @Valid ProductDto dto) {
    return productService.createProduct(dto);
  }

  @Operation(
      summary = "Buscar um produto por ID",
      description = "Busca um produto na base de dados pelo ID."
  )
  @GetMapping(value = "/find/{id}", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto findProductById(@PathVariable String id) {
    return productService.findProductById(id);
  }

  @Operation(
      summary = "Atualizar um produto",
      description = "Atualiza um produto na base de dados pelo ID."
  )
  @PutMapping(value = "/edit/{id}", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ProductDto updateProduct(@PathVariable String id, @RequestBody @Valid ProductDto dto) {
    return productService.updateProduct(id, dto);
  }

  @Operation(
      summary = "Deletar um produto",
      description = "Deleta um produto na base de dados pelo ID."
  )
  @DeleteMapping(value = "/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable String id) {
    productService.deleteProduct(id);
  }

  @Operation(
      summary = "Listar todos os produtos",
      description = "Lista todos os produtos cadastrados na base de dados."
  )
  @GetMapping(value = "/list", produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductDto> findAllProducts() {
    return productService.findAllProducts();
  }
}
