package com.expensetracker.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * One week of a trip (week 1 = trip.startDate..+6 days, week 2 = next 7
 * days, and so on) with its total spend.
 */
public class WeeklySummaryResponse {

    private int weekNumber;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private BigDecimal total;
    private long count;

    public WeeklySummaryResponse() {
    }

    public WeeklySummaryResponse(int weekNumber, LocalDate weekStart, LocalDate weekEnd,
                                  BigDecimal total, long count) {
        this.weekNumber = weekNumber;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.total = total;
        this.count = count;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
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
