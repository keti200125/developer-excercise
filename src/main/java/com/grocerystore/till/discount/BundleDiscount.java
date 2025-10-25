package com.grocerystore.till.discount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grocerystore.till.model.CandidateDiscount;
import com.grocerystore.till.service.ProductService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.*;

@Entity
@DiscriminatorValue("BUNDLE")
@Getter
public class BundleDiscount extends Discount {

    @JsonIgnore
    private String promotionalProducts;

    public BundleDiscount() { }

    public BundleDiscount(String promotionalProducts, int X, int Y, ProductService productService) {
        super(productService, X, Y);
        this.promotionalProducts = promotionalProducts;
    }

    public List<String> getPromotionalProductList() {
        if (promotionalProducts == null || promotionalProducts.isEmpty()) return List.of();
        return Arrays.stream(promotionalProducts.split(",")).map(String::trim).toList();
    }

    @Override
    public List<CandidateDiscount> findApplicableDiscounts(List<String> basket) {
        List<CandidateDiscount> candidates = new ArrayList<>();
        List<Integer> eligible = new ArrayList<>();
        var eligibleNames = getPromotionalProductList();

        for (int i = 0; i < basket.size(); i++)
            if (eligibleNames.contains(basket.get(i))) eligible.add(i);

        while (eligible.size() >= X) {
            List<Integer> group = new ArrayList<>(eligible.subList(0, X));
            List<Integer> prices = new ArrayList<>();
            for (int idx : group)
                prices.add(productService.getProductByName(basket.get(idx)).getPrice());
            prices.sort(Integer::compare);
            int savings = prices.subList(0, X - Y).stream().mapToInt(Integer::intValue).sum();
            candidates.add(new CandidateDiscount(group, savings,
                    String.format("Bundle (%d for %d)", X, Y)));
            eligible.subList(0, X).clear();
        }
        return candidates;
    }
}



