package com.expensetracker.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class FamilyMemberRequest {

    @NotBlank(message = "name is required")
    private String name;

    /** Optional - leave null for "no budget limit set". */
    @DecimalMin(value = "0.00", message = "monthlyBudget cannot be negative")
    private BigDecimal monthlyBudget;

    public FamilyMemberRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }
}
