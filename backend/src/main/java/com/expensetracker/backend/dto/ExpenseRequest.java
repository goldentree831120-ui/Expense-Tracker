package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Payload for creating or updating an expense (request body).
 */
public class ExpenseRequest {

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "category is required")
    private Category category;

    @NotNull(message = "expenseDate is required")
    @PastOrPresent(message = "expenseDate cannot be in the future")
    private LocalDate expenseDate;

    /** Optional - null means this expense isn't attributed to a family member. */
    private Long familyMemberId;

    public ExpenseRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Long getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(Long familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
