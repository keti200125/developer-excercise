package com.grocerystore.till.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountSummary {
    private Long id;
    private String type;
    private String description;
}
