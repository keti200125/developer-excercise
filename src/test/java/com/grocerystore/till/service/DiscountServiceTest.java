package com.grocerystore.till.service;

import com.grocerystore.till.discount.Discount;
import com.grocerystore.till.dto.DiscountRequest;
import com.grocerystore.till.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void cleanDatabase() {
        discountService.getAllDiscounts().forEach(d -> discountService.deleteDiscount(d.getId()));
        productService.getAllProducts().forEach(p -> productService.deleteProduct(p.getName()));
    }

    @BeforeEach
    void setupProducts() {
        productService.addProduct(new Product("apple", 50, "fruit"));
        productService.addProduct(new Product("banana", 40, "fruit"));
        productService.addProduct(new Product("bread", 120, "bakery"));
        productService.addProduct(new Product("potato", 26, "vegetable"));
    }

    @Test
    void testCreateBundleDiscount() {
        DiscountRequest req = new DiscountRequest();
        req.setType("bundle");
        req.setApplicableProducts(List.of("apple", "banana"));
        req.setX(3);
        req.setY(2);

        Discount discount = discountService.createDiscount(req);

        assertNotNull(discount);
        assertTrue(discount instanceof com.grocerystore.till.discount.BundleDiscount);
    }

    @Test
    void testCreateProgressiveDiscount() {
        DiscountRequest req = new DiscountRequest();
        req.setType("progressive");
        req.setProductName("potato");
        req.setX(1);
        req.setY(50);

        Discount discount = discountService.createDiscount(req);

        assertNotNull(discount);
        assertTrue(discount instanceof com.grocerystore.till.discount.ProgressiveDiscount);
    }

    @Test
    void testCreateBulkDiscount() {
        DiscountRequest req = new DiscountRequest();
        req.setType("bulk");
        req.setProductName("bread");
        req.setX(4);
        req.setY(100);

        Discount discount = discountService.createDiscount(req);

        assertNotNull(discount);
        assertTrue(discount instanceof com.grocerystore.till.discount.BulkDiscount);
    }

    @Test
    void testDeleteDiscount() {
        DiscountRequest req = new DiscountRequest();
        req.setType("progressive");
        req.setProductName("banana");
        req.setX(1);
        req.setY(50);

        Discount discount = discountService.createDiscount(req);
        discountService.deleteDiscount(discount.getId());

        List<Discount> remaining = discountService.getAllDiscounts();
        assertTrue(remaining.isEmpty(), "Discount should be deleted successfully");
    }

    @Test
    void testGetAllDiscounts() {
        DiscountRequest req1 = new DiscountRequest();
        req1.setType("bundle");
        req1.setApplicableProducts(List.of("apple", "banana"));
        req1.setX(3);
        req1.setY(2);
        discountService.createDiscount(req1);

        DiscountRequest req2 = new DiscountRequest();
        req2.setType("progressive");
        req2.setProductName("potato");
        req2.setX(1);
        req2.setY(50);
        discountService.createDiscount(req2);

        List<Discount> discounts = discountService.getAllDiscounts();
        assertEquals(2, discounts.size(), "Should have 2 active discounts");
    }
}
