package com.grocerystore.till.discount;

import com.grocerystore.till.model.CandidateDiscount;
import com.grocerystore.till.model.Product;
import com.grocerystore.till.service.ProductService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.*;

@Entity
@DiscriminatorValue("BULK")
@Getter
public class BulkDiscount extends Discount {

    private String productName;

    public BulkDiscount() { }

    public BulkDiscount(String productName, int minQty, int newPrice, ProductService productService) {
        super(productService, minQty, newPrice);
        this.productName = productName;
    }

    @Override
    public List<CandidateDiscount> findApplicableDiscounts(List<String> basket) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
            if (basket.get(i).equals(productName)) indices.add(i);

        if (indices.size() < X) return List.of();

        int savings = 0;
        for (int idx : indices) {
            Product p = productService.getProductByName(basket.get(idx));
            savings += (p.getPrice() - Y);
        }
        return List.of(new CandidateDiscount(indices, savings,
                String.format("%d+ %s for %dc each", X, productName, Y)));
    }
}

