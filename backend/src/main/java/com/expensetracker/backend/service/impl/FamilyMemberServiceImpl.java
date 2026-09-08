package com.expensetracker.backend.service.impl;

import com.expensetracker.backend.dto.FamilyMemberBudgetResponse;
import com.expensetracker.backend.dto.FamilyMemberRequest;
import com.expensetracker.backend.dto.FamilyMemberResponse;
import com.expensetracker.backend.entity.FamilyMember;
import com.expensetracker.backend.exception.DuplicateResourceException;
import com.expensetracker.backend.exception.ResourceNotFoundException;
import com.expensetracker.backend.repository.ExpenseRepository;
import com.expensetracker.backend.repository.FamilyMemberRepository;
import com.expensetracker.backend.service.FamilyMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;
    private final ExpenseRepository expenseRepository;

    public FamilyMemberServiceImpl(FamilyMemberRepository familyMemberRepository,
                                    ExpenseRepository expenseRepository) {
        this.familyMemberRepository = familyMemberRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public FamilyMemberResponse create(FamilyMemberRequest request) {
        if (familyMemberRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("A family member named '" + request.getName() + "' already exists");
        }
        FamilyMember member = new FamilyMember(request.getName(), request.getMonthlyBudget());
        FamilyMember saved = familyMemberRepository.save(member);
        return FamilyMemberResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyMemberResponse> getAll() {
        return familyMemberRepository.findAll()
                .stream()
                .map(FamilyMemberResponse::fromEntity)
                .toList();
    }

    @Override
    public FamilyMemberResponse update(Long id, FamilyMemberRequest request) {
        FamilyMember member = findMemberOrThrow(id);
        if (!member.getName().equalsIgnoreCase(request.getName())
                && familyMemberRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("A family member named '" + request.getName() + "' already exists");
        }
        member.setName(request.getName());
        member.setMonthlyBudget(request.getMonthlyBudget());
        FamilyMember saved = familyMemberRepository.save(member);
        return FamilyMemberResponse.fromEntity(saved);
    }

    @Override
    public void delete(Long id) {
        FamilyMember member = findMemberOrThrow(id);
        familyMemberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyMemberBudgetResponse> getBudgetSummaries() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        return familyMemberRepository.findAll()
                .stream()
                .map(member -> {
                    BigDecimal spent = expenseRepository.sumByMemberBetween(member.getId(), start, end);
                    return new FamilyMemberBudgetResponse(
                            member.getId(),
                            member.getName(),
                            member.getMonthlyBudget(),
                            spent
                    );
                })
                .toList();
    }

    private FamilyMember findMemberOrThrow(Long id) {
        return familyMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family member not found with id: " + id));
    }
}
