package com.expensetracker.backend.controller;

import com.expensetracker.backend.dto.CategorySummaryResponse;
import com.expensetracker.backend.dto.ExpenseRequest;
import com.expensetracker.backend.dto.ExpenseResponse;
import com.expensetracker.backend.dto.MonthlySummaryResponse;
import com.expensetracker.backend.entity.Category;
import com.expensetracker.backend.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse created = expenseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Long familyMemberId) {
        List<ExpenseResponse> expenses;
        if (familyMemberId != null) {
            expenses = expenseService.getByFamilyMember(familyMemberId);
        } else if (category != null) {
            expenses = expenseService.getByCategory(category);
        } else {
            expenses = expenseService.getAll();
        }
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/by-category")
    public ResponseEntity<List<CategorySummaryResponse>> summaryByCategory() {
        return ResponseEntity.ok(expenseService.getSummaryByCategory());
    }

    @GetMapping("/summary/by-month")
    public ResponseEntity<List<MonthlySummaryResponse>> summaryByMonth() {
        return ResponseEntity.ok(expenseService.getSummaryByMonth());
    }
}
