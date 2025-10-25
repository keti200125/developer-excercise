package com.grocerystore.till.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class CandidateDiscount implements Comparable<CandidateDiscount> {

    private final List<Integer> itemIndices;
    private final int savings;
    private final String description;

    public CandidateDiscount(List<Integer> itemIndices, int savings, String description) {
        this.itemIndices = itemIndices;
        this.savings = savings;
        this.description = description;
    }

    @Override
    public int compareTo(CandidateDiscount o) {
        int cmp = Integer.compare(o.savings, this.savings);
        return cmp != 0 ? cmp : Integer.compare(this.itemIndices.size(), o.itemIndices.size());
    }
}

