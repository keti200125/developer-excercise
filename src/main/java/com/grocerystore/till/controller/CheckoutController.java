package com.grocerystore.till.controller;

import com.grocerystore.till.model.Receipt;
import com.grocerystore.till.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public Receipt checkout(@RequestBody List<String> scannedItems) {
        return checkoutService.checkout(scannedItems);
    }
}
