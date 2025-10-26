package com.grocerystore.till.service;

import com.grocerystore.till.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @BeforeEach
    void clean() {
        productService.getAllProducts().forEach(p -> productService.deleteProduct(p.getName()));
    }

    @Test
    void testAddAndRetrieveProduct() {
        Product p = new Product("apple", 50, "fruit");
        productService.addProduct(p);

        Product fetched = productService.getProductByName("apple");
        assertEquals(50, fetched.getPrice());
        assertEquals("fruit", fetched.getCategory());
    }

    @Test
    void testDuplicateProductThrowsError() {
        productService.addProduct(new Product("apple", 50, "fruit"));
        assertThrows(IllegalArgumentException.class,
                () -> productService.addProduct(new Product("apple", 60, "fruit")));
    }


    @Test
    void testDeleteProduct() {
        productService.addProduct(new Product("apple", 50, "fruit"));
        productService.deleteProduct("apple");
        assertThrows(IllegalArgumentException.class, () -> productService.getProductByName("apple"));
    }

    @Test
    void testGetAllProducts() {
        productService.addProduct(new Product("apple", 50, "fruit"));
        productService.addProduct(new Product("banana", 40, "fruit"));

        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }
}
