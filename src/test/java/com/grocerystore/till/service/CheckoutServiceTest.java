package com.grocerystore.till.service;

import com.grocerystore.till.dto.DiscountRequest;
import com.grocerystore.till.model.Product;
import com.grocerystore.till.model.Receipt;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private DiscountService discountService;

    private int parseTotal(String formatted) {
        if (formatted == null || formatted.isBlank()) return 0;
        String[] parts = formatted.split(" ");
        try {
            int aws = Integer.parseInt(parts[0]);
            int clouds = Integer.parseInt(parts[3]);
            return aws * 100 + clouds;
        } catch (Exception e) {
            return 0;
        }
    }

    @Test
    void testEmptyBasket() {
        Receipt r = checkoutService.checkout(List.of());
        assertEquals("0 aws and 0 clouds", r.getTotal());
    }

    @Test
    void testSingleItemNoDiscount() {
        productService.addProduct(new Product("apple", 50, "fruit"));

        Receipt r = checkoutService.checkout(List.of("apple"));
        assertEquals("0 aws and 50 clouds", r.getTotal());
    }

    @Test
    void testInvalidProductThrowsError() {
        assertThrows(IllegalArgumentException.class,
                () -> checkoutService.checkout(List.of("unicorn")));
    }

    @Test
    void testMultipleItemsWithDiscounts() {
        productService.addProduct(new Product("apple", 50, "fruit"));
        productService.addProduct(new Product("banana", 40, "fruit"));
        productService.addProduct(new Product("tomato", 30, "vegetable"));
        productService.addProduct(new Product("potato", 26, "vegetable"));
        productService.addProduct(new Product("bread", 120, "bakery"));
        productService.addProduct(new Product("milk", 95, "dairy"));

        discountService.createDiscount(new DiscountRequest() {{
            setType("bundle");
            setApplicableProducts(List.of("apple", "banana", "tomato"));
            setX(3);
            setY(2);
        }});

        discountService.createDiscount(new DiscountRequest() {{
            setType("progressive");
            setProductName("potato");
            setX(1);
            setY(50);
        }});

        discountService.createDiscount(new DiscountRequest() {{
            setType("bulk");
            setProductName("bread");
            setX(4);
            setY(100);
        }});

        List<String> basket = List.of("apple", "banana", "banana", "potato", "tomato", "banana", "potato");
        Receipt r = checkoutService.checkout(basket);

        assertNotNull(r);
        int before = parseTotal(r.getTotalBeforeDiscount());
        int after = parseTotal(r.getTotal());

        System.out.println("Before discount: " + before + " After: " + after);
        assertTrue(before > after, "Discount should reduce total price");

        productService.getAllProducts().forEach(p -> productService.deleteProduct(p.getName()));
    }
}
