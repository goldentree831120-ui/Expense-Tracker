package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.Category;

import java.math.BigDecimal;

/**
 * Total spent in one category (used for the "summary by category" view).
 */
public class CategorySummaryResponse {

    private Category category;
    private BigDecimal total;
    private long count;

    public CategorySummaryResponse() {
    }

    public CategorySummaryResponse(Category category, BigDecimal total, long count) {
        this.category = category;
        this.total = total;
        this.count = count;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
