package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.Trip;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TripResponse {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private BigDecimal totalSpent;
    private long expenseCount;

    public TripResponse() {
    }

    public static TripResponse fromEntity(Trip trip, BigDecimal totalSpent, long expenseCount) {
        TripResponse response = new TripResponse();
        response.setId(trip.getId());
        response.setName(trip.getName());
        response.setStartDate(trip.getStartDate());
        response.setEndDate(trip.getEndDate());
        response.setDescription(trip.getDescription());
        response.setTotalSpent(totalSpent);
        response.setExpenseCount(expenseCount);
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public long getExpenseCount() {
        return expenseCount;
    }

    public void setExpenseCount(long expenseCount) {
        this.expenseCount = expenseCount;
    }
}
