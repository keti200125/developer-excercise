package com.grocerystore.till.controller;

import com.grocerystore.till.model.Product;
import com.grocerystore.till.service.ProductService;
import com.grocerystore.till.dto.ProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product saved = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PutMapping("/{name}")
    public Product update(@PathVariable String name, @Valid @RequestBody ProductUpdateRequest req) {
        return productService.updateProduct(name, req);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
    }
}
