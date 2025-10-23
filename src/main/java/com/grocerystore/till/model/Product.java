package com.grocerystore.till.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
public class Product {

    @Id
    private String name;
    private double price;
    private String category;

    private int quantity; // I know it is not the best way to handle
                          // the problem (future update ?)

    public Product(String name, double price, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }
}
