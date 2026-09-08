package com.expensetracker.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * A household member expenses can be attributed to, with an optional
 * monthly budget so spending can be tracked against a limit.
 */
@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    /** Nullable - a member without a set budget just gets tracked, not limited. */
    @Column(name = "monthly_budget", precision = 10, scale = 2)
    private BigDecimal monthlyBudget;

    public FamilyMember() {
    }

    public FamilyMember(String name, BigDecimal monthlyBudget) {
        this.name = name;
        this.monthlyBudget = monthlyBudget;
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
