package com.expensetracker.backend.service;

import com.expensetracker.backend.dto.FamilyMemberBudgetResponse;
import com.expensetracker.backend.dto.FamilyMemberRequest;
import com.expensetracker.backend.dto.FamilyMemberResponse;

import java.util.List;

public interface FamilyMemberService {

    FamilyMemberResponse create(FamilyMemberRequest request);

    List<FamilyMemberResponse> getAll();

    FamilyMemberResponse update(Long id, FamilyMemberRequest request);

    void delete(Long id);

    List<FamilyMemberBudgetResponse> getBudgetSummaries();
}
