package com.grocerystore.till.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Min(value = 1, message = "Price must be at least 1 cloud")
    @Column(nullable = false)
    private int price;

    @NotBlank(message = "Category cannot be blank")
    @Column(nullable = false)
    private String category;

    public Product(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getFormattedPrice() {
        return (price / 100) + " aws " + (price % 100) + " clouds";
    }
}
