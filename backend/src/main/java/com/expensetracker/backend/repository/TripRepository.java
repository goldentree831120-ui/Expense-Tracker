package com.expensetracker.backend.repository;

import com.expensetracker.backend.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
