package com.expensetracker.backend.dto;

import com.expensetracker.backend.entity.FamilyMember;

import java.math.BigDecimal;

public class FamilyMemberResponse {

    private Long id;
    private String name;
    private BigDecimal monthlyBudget;

    public FamilyMemberResponse() {
    }

    public static FamilyMemberResponse fromEntity(FamilyMember member) {
        FamilyMemberResponse response = new FamilyMemberResponse();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setMonthlyBudget(member.getMonthlyBudget());
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

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }
}
