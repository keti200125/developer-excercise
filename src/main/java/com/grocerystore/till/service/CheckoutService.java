package com.grocerystore.till.service;

import com.grocerystore.till.discount.Discount;
import com.grocerystore.till.dto.DiscountView;
import com.grocerystore.till.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductService productService;
    private final DiscountService discountService;

    public Receipt checkout(List<String> basket) {
        List<Product> products = basket.stream()
                .map(productService::getProductByName)
                .toList();

        List<CandidateDiscount> candidates = new ArrayList<>();
        for (Discount promo : discountService.getAllDiscounts()) {
            promo.setProductService(productService);
            candidates.addAll(promo.findApplicableDiscounts(basket));
        }

        candidates.sort(null);
        Set<Integer> used = new HashSet<>();
        List<DiscountView> applied = new ArrayList<>();
        int totalWithout = products.stream().mapToInt(Product::getPrice).sum();
        int savings = 0;

        for (CandidateDiscount c : candidates) {
            if (c.getItemIndices().stream().noneMatch(used::contains)) {
                used.addAll(c.getItemIndices());
                savings += c.getSavings();

                List<String> names = c.getItemIndices().stream()
                        .map(basket::get)
                        .toList();

                applied.add(new DiscountView(names, c.getSavings(), c.getDescription()));
            }
        }

        int total = totalWithout - savings;

        return new Receipt(
                products.stream().map(p ->
                        new ReceiptItem(p.getName(), p.getPrice())).toList(),
                applied,
                format(totalWithout),
                format(total)
        );
    }

    private String format(int clouds) {
        return String.format("%d aws and %d clouds", clouds / 100, clouds % 100);
    }
}
