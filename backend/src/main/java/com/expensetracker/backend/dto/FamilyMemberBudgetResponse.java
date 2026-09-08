package com.expensetracker.backend.dto;

import java.math.BigDecimal;

/**
 * A member's spending for the CURRENT calendar month compared against their
 * monthly budget (if they have one set).
 */
public class FamilyMemberBudgetResponse {

    private Long memberId;
    private String memberName;
    private BigDecimal monthlyBudget;
    private BigDecimal spentThisMonth;
    private BigDecimal remaining;

    public FamilyMemberBudgetResponse() {
    }

    public FamilyMemberBudgetResponse(Long memberId, String memberName, BigDecimal monthlyBudget,
                                       BigDecimal spentThisMonth) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.monthlyBudget = monthlyBudget;
        this.spentThisMonth = spentThisMonth;
        this.remaining = monthlyBudget != null ? monthlyBudget.subtract(spentThisMonth) : null;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public BigDecimal getSpentThisMonth() {
        return spentThisMonth;
    }

    public void setSpentThisMonth(BigDecimal spentThisMonth) {
        this.spentThisMonth = spentThisMonth;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
}
