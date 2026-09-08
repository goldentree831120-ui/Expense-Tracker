package com.expensetracker.backend.repository;

import com.expensetracker.backend.entity.TripExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TripExpenseRepository extends JpaRepository<TripExpense, Long> {

    List<TripExpense> findByTripIdOrderByExpenseDateAsc(Long tripId);

    long countByTripId(Long tripId);

    @Query("SELECT COALESCE(SUM(te.amount), 0) FROM TripExpense te WHERE te.trip.id = :tripId")
    BigDecimal sumByTripId(@Param("tripId") Long tripId);
}
