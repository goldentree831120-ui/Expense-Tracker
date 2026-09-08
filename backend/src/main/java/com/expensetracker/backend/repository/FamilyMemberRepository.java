package com.expensetracker.backend.repository;

import com.expensetracker.backend.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    boolean existsByNameIgnoreCase(String name);
}
