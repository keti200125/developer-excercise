package com.grocerystore.till.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    @Min(value = 1, message = "Price must be at least 1 cloud")
    private int price;

    @NotBlank(message = "Category cannot be blank")
    private String category;
}
