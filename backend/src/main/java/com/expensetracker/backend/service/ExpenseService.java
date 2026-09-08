package com.expensetracker.backend.service;

import com.expensetracker.backend.dto.CategorySummaryResponse;
import com.expensetracker.backend.dto.ExpenseRequest;
import com.expensetracker.backend.dto.ExpenseResponse;
import com.expensetracker.backend.dto.MonthlySummaryResponse;
import com.expensetracker.backend.entity.Category;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse create(ExpenseRequest request);

    ExpenseResponse getById(Long id);

    List<ExpenseResponse> getAll();

    List<ExpenseResponse> getByCategory(Category category);

    List<ExpenseResponse> getByFamilyMember(Long familyMemberId);

    ExpenseResponse update(Long id, ExpenseRequest request);

    void delete(Long id);

    List<CategorySummaryResponse> getSummaryByCategory();

    List<MonthlySummaryResponse> getSummaryByMonth();
}
