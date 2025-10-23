package com.grocerystore.till.service;

import com.grocerystore.till.model.Product;
import com.grocerystore.till.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        if (productRepository.existsById(product.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(String name, Product updatedProduct) {
        Product existing = productRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());
        existing.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existing);
    }

    public void deleteProduct(String name) {
        if (!productRepository.existsById(name)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(name);
    }

    public Product getProductByName(String name) {
        return productRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}


