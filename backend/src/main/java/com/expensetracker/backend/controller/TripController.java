package com.expensetracker.backend.controller;

import com.expensetracker.backend.dto.TripExpenseRequest;
import com.expensetracker.backend.dto.TripExpenseResponse;
import com.expensetracker.backend.dto.TripRequest;
import com.expensetracker.backend.dto.TripResponse;
import com.expensetracker.backend.dto.WeeklySummaryResponse;
import com.expensetracker.backend.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> create(@Valid @RequestBody TripRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAll() {
        return ResponseEntity.ok(tripService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/expenses")
    public ResponseEntity<TripExpenseResponse> addExpense(@PathVariable Long id,
                                                            @Valid @RequestBody TripExpenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.addExpense(id, request));
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<TripExpenseResponse>> getExpenses(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getExpenses(id));
    }

    @DeleteMapping("/{id}/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, @PathVariable Long expenseId) {
        tripService.deleteExpense(id, expenseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/summary/weekly")
    public ResponseEntity<List<WeeklySummaryResponse>> weeklySummary(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getWeeklySummary(id));
    }
}
