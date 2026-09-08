package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.TripExpense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TripExpenseResponse {

    private Long id;
    private Long tripId;
    private String description;
    private BigDecimal amount;
    private String paidBy;
    private LocalDate expenseDate;

    public TripExpenseResponse() {
    }

    public static TripExpenseResponse fromEntity(TripExpense expense) {
        TripExpenseResponse response = new TripExpenseResponse();
        response.setId(expense.getId());
        response.setTripId(expense.getTrip().getId());
        response.setDescription(expense.getDescription());
        response.setAmount(expense.getAmount());
        response.setPaidBy(expense.getPaidBy());
        response.setExpenseDate(expense.getExpenseDate());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }
}
