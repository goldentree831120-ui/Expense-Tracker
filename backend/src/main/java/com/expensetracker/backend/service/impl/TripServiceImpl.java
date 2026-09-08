package com.expensetracker.backend.service.impl;

import com.expensetracker.backend.dto.TripExpenseRequest;
import com.expensetracker.backend.dto.TripExpenseResponse;
import com.expensetracker.backend.dto.TripRequest;
import com.expensetracker.backend.dto.TripResponse;
import com.expensetracker.backend.dto.WeeklySummaryResponse;
import com.expensetracker.backend.entity.Trip;
import com.expensetracker.backend.entity.TripExpense;
import com.expensetracker.backend.exception.ResourceNotFoundException;
import com.expensetracker.backend.repository.TripExpenseRepository;
import com.expensetracker.backend.repository.TripRepository;
import com.expensetracker.backend.service.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripExpenseRepository tripExpenseRepository;

    public TripServiceImpl(TripRepository tripRepository, TripExpenseRepository tripExpenseRepository) {
        this.tripRepository = tripRepository;
        this.tripExpenseRepository = tripExpenseRepository;
    }

    @Override
    public TripResponse create(TripRequest request) {
        Trip trip = new Trip(request.getName(), request.getStartDate(), request.getEndDate(), request.getDescription());
        Trip saved = tripRepository.save(trip);
        return TripResponse.fromEntity(saved, BigDecimal.ZERO, 0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripResponse> getAll() {
        return tripRepository.findAll()
                .stream()
                .map(trip -> TripResponse.fromEntity(
                        trip,
                        tripExpenseRepository.sumByTripId(trip.getId()),
                        tripExpenseRepository.countByTripId(trip.getId())
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TripResponse getById(Long id) {
        Trip trip = findTripOrThrow(id);
        return TripResponse.fromEntity(
                trip,
                tripExpenseRepository.sumByTripId(id),
                tripExpenseRepository.countByTripId(id)
        );
    }

    @Override
    public void delete(Long id) {
        Trip trip = findTripOrThrow(id);
        tripRepository.delete(trip);
    }

    @Override
    public TripExpenseResponse addExpense(Long tripId, TripExpenseRequest request) {
        Trip trip = findTripOrThrow(tripId);
        TripExpense expense = new TripExpense(
                trip,
                request.getDescription(),
                request.getAmount(),
                request.getPaidBy(),
                request.getExpenseDate()
        );
        TripExpense saved = tripExpenseRepository.save(expense);
        return TripExpenseResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripExpenseResponse> getExpenses(Long tripId) {
        findTripOrThrow(tripId);
        return tripExpenseRepository.findByTripIdOrderByExpenseDateAsc(tripId)
                .stream()
                .map(TripExpenseResponse::fromEntity)
                .toList();
    }

    @Override
    public void deleteExpense(Long tripId, Long expenseId) {
        findTripOrThrow(tripId);
        TripExpense expense = tripExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip expense not found with id: " + expenseId));
        if (!expense.getTrip().getId().equals(tripId)) {
            throw new ResourceNotFoundException("Trip expense " + expenseId + " does not belong to trip " + tripId);
        }
        tripExpenseRepository.delete(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklySummaryResponse> getWeeklySummary(Long tripId) {
        Trip trip = findTripOrThrow(tripId);
        LocalDate tripStart = trip.getStartDate();

        List<TripExpense> expenses = tripExpenseRepository.findByTripIdOrderByExpenseDateAsc(tripId);

        // Group expenses into 7-day buckets counted from the trip's start date
        // (week 1 = start..start+6, week 2 = start+7..start+13, etc.) rather
        // than calendar weeks, since a trip rarely starts on a Sunday/Monday.
        Map<Integer, List<TripExpense>> byWeek = new LinkedHashMap<>();
        for (TripExpense expense : expenses) {
            long daysSinceStart = ChronoUnit.DAYS.between(tripStart, expense.getExpenseDate());
            int weekNumber = (int) (Math.max(daysSinceStart, 0) / 7) + 1;
            byWeek.computeIfAbsent(weekNumber, k -> new java.util.ArrayList<>()).add(expense);
        }

        return byWeek.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    int weekNumber = entry.getKey();
                    List<TripExpense> weekExpenses = entry.getValue();
                    LocalDate weekStart = tripStart.plusDays((long) (weekNumber - 1) * 7);
                    LocalDate weekEnd = weekStart.plusDays(6);
                    BigDecimal total = weekExpenses.stream()
                            .map(TripExpense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new WeeklySummaryResponse(weekNumber, weekStart, weekEnd, total, weekExpenses.size());
                })
                .toList();
    }

    private Trip findTripOrThrow(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
    }
}
