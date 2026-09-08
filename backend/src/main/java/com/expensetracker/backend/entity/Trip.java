package com.expensetracker.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * A group trip / event that a group of people log shared expenses against
 * over its duration (e.g. "Goa Trip - Aug 2026").
 */
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    /** Required - weekly evaluation is computed relative to this date. */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** Optional - null means the trip is still ongoing. */
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(length = 500)
    private String description;

    public Trip() {
    }

    public Trip(String name, LocalDate startDate, LocalDate endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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
}
