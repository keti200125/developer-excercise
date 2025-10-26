/*
package com.grocerystore.till.config;

import com.grocerystore.till.discount.*;
import com.grocerystore.till.model.Product;
import com.grocerystore.till.service.ProductService;
import com.grocerystore.till.service.DiscountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ProductService productService, DiscountService discountService) {
        return args -> {
            productService.addProduct(new Product("apple", 50, "fruit"));
            productService.addProduct(new Product("banana", 40, "fruit"));
            productService.addProduct(new Product("tomato", 30, "vegetable"));
            productService.addProduct(new Product("potato", 26, "vegetable"));
            productService.addProduct(new Product("bread", 120, "bakery"));
            productService.addProduct(new Product("milk", 95, "dairy"));

            discountService.createDiscount(
                    new com.grocerystore.till.dto.DiscountRequest() {{
                        setType("bundle");
                        setApplicableProducts(java.util.List.of("apple", "banana", "tomato"));
                        setX(3);
                        setY(2);
                    }}
            );

            discountService.createDiscount(
                    new com.grocerystore.till.dto.DiscountRequest() {{
                        setType("progressive");
                        setProductName("potato");
                        setX(1);
                        setY(50);
                    }}
            );

            discountService.createDiscount(
                    new com.grocerystore.till.dto.DiscountRequest() {{
                        setType("bulk");
                        setProductName("bread");
                        setX(4);
                        setY(100);
                    }}
            );
        };
    }
}
*/