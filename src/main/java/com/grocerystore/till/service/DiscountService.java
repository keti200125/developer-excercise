package com.grocerystore.till.service;

import com.grocerystore.till.discount.*;
import com.grocerystore.till.dto.DiscountRequest;
import com.grocerystore.till.dto.DiscountSummary;
import com.grocerystore.till.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final ProductService productService;
    private final DiscountRepository repo;

    public List<Discount> getAllDiscounts() { return repo.findAll(); }

    public Discount getDiscountById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Discount not found"));
    }

    public Discount createDiscount(DiscountRequest r) {
        if (r.getType() == null)
            throw new IllegalArgumentException("Discount type is required");

        if (r.getX() == null || r.getY() == null || r.getX() <= 0 || r.getY() <= 0) {
            throw new IllegalArgumentException("X and Y must be positive numbers");
        }


        Discount d = switch (r.getType()) {
            case "bundle" -> {
                if (r.getApplicableProducts() == null || r.getApplicableProducts().isEmpty())
                    throw new IllegalArgumentException("Bundle discount requires applicableProducts");
                yield new BundleDiscount(
                        String.join(",", r.getApplicableProducts()),
                        safeInt(r.getX()),
                        safeInt(r.getY()),
                        productService
                );
            }
            case "progressive" -> {
                if (r.getProductName() == null)
                    throw new IllegalArgumentException("Progressive discount requires productName");
                yield new ProgressiveDiscount(
                        r.getProductName(),
                        safeInt(r.getX()),
                        safeInt(r.getY()),
                        productService
                );
            }
            case "bulk" -> {
                if (r.getProductName() == null)
                    throw new IllegalArgumentException("Bulk discount requires productName");
                yield new BulkDiscount(
                        r.getProductName(),
                        safeInt(r.getX()),
                        safeInt(r.getY()),
                        productService
                );
            }
            default -> throw new IllegalArgumentException("Invalid discount type: " + r.getType());
        };

        return repo.save(d);
    }

    private int safeInt(Integer value) {
        if (value == null) throw new IllegalArgumentException("Missing numeric field (X or Y)");
        return value;
    }


    public void deleteDiscount(Long id) { repo.deleteById(id); }

    public List<DiscountSummary> getAllSummaries() {
        return getAllDiscounts().stream().map(d -> {
            if (d instanceof BundleDiscount b)
                return new DiscountSummary(b.getId(), "Bundle",
                        String.format("%d for %d on %s", b.getX(), b.getY(), b.getPromotionalProductList()));
            if (d instanceof ProgressiveDiscount p)
                return new DiscountSummary(p.getId(), "Progressive",
                        String.format("Buy %d get next %d%% off %s", p.getX(), p.getY(), p.getProductName()));
            if (d instanceof BulkDiscount b)
                return new DiscountSummary(b.getId(), "Bulk",
                        String.format("%d+ %s for %dc each", b.getX(), b.getProductName(), b.getY()));
            return new DiscountSummary(d.getId(), "Unknown", "N/A");
        }).toList();
    }
}
