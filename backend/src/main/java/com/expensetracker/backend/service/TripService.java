package com.expensetracker.backend.service;

import com.expensetracker.backend.dto.TripExpenseRequest;
import com.expensetracker.backend.dto.TripExpenseResponse;
import com.expensetracker.backend.dto.TripRequest;
import com.expensetracker.backend.dto.TripResponse;
import com.expensetracker.backend.dto.WeeklySummaryResponse;

import java.util.List;

public interface TripService {

    TripResponse create(TripRequest request);

    List<TripResponse> getAll();

    TripResponse getById(Long id);

    void delete(Long id);

    TripExpenseResponse addExpense(Long tripId, TripExpenseRequest request);

    List<TripExpenseResponse> getExpenses(Long tripId);

    void deleteExpense(Long tripId, Long expenseId);

    List<WeeklySummaryResponse> getWeeklySummary(Long tripId);
}
