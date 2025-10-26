package com.grocerystore.till.model;

import com.grocerystore.till.dto.DiscountView;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Receipt {
    private List<ReceiptItem> items;
    private List<DiscountView> discounts;
    private String totalBeforeDiscount;
    private String total;
}
