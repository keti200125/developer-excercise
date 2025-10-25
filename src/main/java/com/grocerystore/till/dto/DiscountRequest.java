package com.grocerystore.till.dto;

import lombok.Data;
import java.util.List;

@Data
public class DiscountRequest {
    private String type;
    private String productName;
    private Integer X;
    private Integer Y;
    private List<String> applicableProducts;
}
