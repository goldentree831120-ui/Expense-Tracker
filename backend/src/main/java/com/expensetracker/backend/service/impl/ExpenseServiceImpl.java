package com.expensetracker.backend.service.impl;

import com.expensetracker.backend.dto.CategorySummaryResponse;
import com.expensetracker.backend.dto.ExpenseRequest;
import com.expensetracker.backend.dto.ExpenseResponse;
import com.expensetracker.backend.dto.MonthlySummaryResponse;
import com.expensetracker.backend.entity.Category;
import com.expensetracker.backend.entity.Expense;
import com.expensetracker.backend.entity.FamilyMember;
import com.expensetracker.backend.exception.ResourceNotFoundException;
import com.expensetracker.backend.repository.ExpenseRepository;
import com.expensetracker.backend.repository.FamilyMemberRepository;
import com.expensetracker.backend.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, FamilyMemberRepository familyMemberRepository) {
        this.expenseRepository = expenseRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    @Override
    public ExpenseResponse create(ExpenseRequest request) {
        Expense expense = new Expense(
                request.getDescription(),
                request.getAmount(),
                request.getCategory(),
                request.getExpenseDate()
        );
        expense.setFamilyMember(resolveFamilyMember(request.getFamilyMemberId()));
        Expense saved = expenseRepository.save(expense);
        return ExpenseResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponse getById(Long id) {
        return ExpenseResponse.fromEntity(findExpenseOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAll() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getByCategory(Category category) {
        return expenseRepository.findByCategory(category)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .toList();
    }

    @Override
    public ExpenseResponse update(Long id, ExpenseRequest request) {
        Expense expense = findExpenseOrThrow(id);
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setFamilyMember(resolveFamilyMember(request.getFamilyMemberId()));
        Expense saved = expenseRepository.save(expense);
        return ExpenseResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getByFamilyMember(Long familyMemberId) {
        return expenseRepository.findByFamilyMemberId(familyMemberId)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .toList();
    }

    private FamilyMember resolveFamilyMember(Long familyMemberId) {
        if (familyMemberId == null) {
            return null;
        }
        return familyMemberRepository.findById(familyMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Family member not found with id: " + familyMemberId));
    }

    @Override
    public void delete(Long id) {
        Expense expense = findExpenseOrThrow(id);
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorySummaryResponse> getSummaryByCategory() {
        return expenseRepository.sumByCategory()
                .stream()
                .map(row -> new CategorySummaryResponse(row.getCategory(), row.getTotal(), row.getCount()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlySummaryResponse> getSummaryByMonth() {
        return expenseRepository.sumByMonth()
                .stream()
                .map(row -> new MonthlySummaryResponse(row.getExpenseYear(), row.getExpenseMonth(), row.getTotal(), row.getCount()))
                .toList();
    }

    private Expense findExpenseOrThrow(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }
}
