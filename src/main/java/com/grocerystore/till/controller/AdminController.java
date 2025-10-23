package com.grocerystore.till.controller;

import com.grocerystore.till.model.Product;
import com.grocerystore.till.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/product/{name}")
    public Product updateProduct(@PathVariable String name, @RequestBody Product updatedProduct) {
        return productService.updateProduct(name, updatedProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @DeleteMapping("/product/{name}")
    public String deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
        return "Product " + name + " deleted successfully.";
    }
}
