package com.grocerystore.till.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DiscountView {
    private List<String> productNames;
    private int savings;
    private String description;
}
