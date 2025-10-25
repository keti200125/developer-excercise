package com.grocerystore.till.discount;

import com.grocerystore.till.model.CandidateDiscount;
import com.grocerystore.till.model.Product;
import com.grocerystore.till.service.ProductService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.*;

@Entity
@DiscriminatorValue("PROGRESSIVE")
@Getter
public class ProgressiveDiscount extends Discount {

    private String productName;

    public ProgressiveDiscount() { }

    public ProgressiveDiscount(String productName, int buyQty, int pctOff, ProductService productService) {
        super(productService, buyQty, pctOff);
        this.productName = productName;
    }

    @Override
    public List<CandidateDiscount> findApplicableDiscounts(List<String> basket) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < basket.size(); i++)
            if (basket.get(i).equals(productName)) indices.add(i);

        List<CandidateDiscount> result = new ArrayList<>();
        int i = 0;
        while (i + X < indices.size()) {
            int discountedIndex = indices.get(i + X);
            Product p = productService.getProductByName(productName);
            int saving = (int) Math.round(p.getPrice() * (Y / 100.0));
            result.add(new CandidateDiscount(List.of(discountedIndex), saving,
                    String.format("Progressive %d%% off %s", Y, productName)));
            i += X + 1;
        }
        return result;
    }
}
