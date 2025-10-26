package com.grocerystore.till.model;

import lombok.Getter;

@Getter
public class ReceiptItem {
    private final String name;
    private final int price;

    public ReceiptItem(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

