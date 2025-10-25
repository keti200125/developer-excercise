package com.grocerystore.till.service;

import com.grocerystore.till.dto.ProductUpdateRequest;
import com.grocerystore.till.model.Product;
import com.grocerystore.till.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    public Product addProduct(Product p) {
        if (repo.existsById(p.getName()))
            throw new IllegalArgumentException("Product already exists");
        return repo.save(p);
    }

    public Product updateProduct(String name, @Valid ProductUpdateRequest updated) {
        Product existing = repo.findById(name).orElseThrow(() ->
                new IllegalArgumentException("Product not found"));
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        return repo.save(existing);
    }

    public void deleteProduct(String name) { repo.deleteById(name); }

    public Product getProductByName(String name) {
        return repo.findById(name).orElseThrow(() ->
                new IllegalArgumentException("Product not found"));
    }

    public List<Product> getAllProducts() { return repo.findAll(); }
}

