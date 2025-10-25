package com.grocerystore.till.discount;

import com.grocerystore.till.model.CandidateDiscount;
import com.grocerystore.till.service.ProductService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_type")
@Getter
@Setter
public abstract class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Transient
    protected ProductService productService;

    protected int X;
    protected int Y;

    public Discount() { }

    public Discount(ProductService productService, int X, int Y) {
        this.productService = productService;
        this.X = X;
        this.Y = Y;
    }

    public abstract List<CandidateDiscount> findApplicableDiscounts(List<String> basket);
}

