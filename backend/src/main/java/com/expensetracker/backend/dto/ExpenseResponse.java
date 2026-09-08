package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.Category;
import com.expensetracker.backend.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * What we send back to the client for a single expense.
 */
public class ExpenseResponse {

    private Long id;
    private String description;
    private BigDecimal amount;
    private Category category;
    private LocalDate expenseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long familyMemberId;
    private String familyMemberName;

    public ExpenseResponse() {
    }

    public static ExpenseResponse fromEntity(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setDescription(expense.getDescription());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setExpenseDate(expense.getExpenseDate());
        response.setCreatedAt(expense.getCreatedAt());
        response.setUpdatedAt(expense.getUpdatedAt());
        if (expense.getFamilyMember() != null) {
            response.setFamilyMemberId(expense.getFamilyMember().getId());
            response.setFamilyMemberName(expense.getFamilyMember().getName());
        }
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(Long familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }
}
