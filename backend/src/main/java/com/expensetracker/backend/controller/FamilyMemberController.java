package com.expensetracker.backend.controller;

import com.expensetracker.backend.dto.FamilyMemberBudgetResponse;
import com.expensetracker.backend.dto.FamilyMemberRequest;
import com.expensetracker.backend.dto.FamilyMemberResponse;
import com.expensetracker.backend.service.FamilyMemberService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/family-members")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    public FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @PostMapping
    public ResponseEntity<FamilyMemberResponse> create(@Valid @RequestBody FamilyMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(familyMemberService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<FamilyMemberResponse>> getAll() {
        return ResponseEntity.ok(familyMemberService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody FamilyMemberRequest request) {
        return ResponseEntity.ok(familyMemberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        familyMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/budget-summary")
    public ResponseEntity<List<FamilyMemberBudgetResponse>> budgetSummary() {
        return ResponseEntity.ok(familyMemberService.getBudgetSummaries());
    }
}
