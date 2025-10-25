package com.grocerystore.till.controller;

import com.grocerystore.till.discount.Discount;
import com.grocerystore.till.dto.DiscountRequest;
import com.grocerystore.till.dto.DiscountSummary;
import com.grocerystore.till.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<DiscountSummary> getAllSummaries() {
        return discountService.getAllSummaries();
    }

    @GetMapping("/{id}")
    public Discount getById(@PathVariable Long id) {
        return discountService.getDiscountById(id);
    }

    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountRequest request) {
        Discount saved = discountService.createDiscount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
    }
}
