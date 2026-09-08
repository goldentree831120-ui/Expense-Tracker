package com.expensetracker.backend.repository;

import com.expensetracker.backend.entity.Category;
import com.expensetracker.backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(Category category);

    List<Expense> findByExpenseDateBetween(LocalDate start, LocalDate end);

    List<Expense> findByFamilyMemberId(Long familyMemberId);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.familyMember.id = :memberId
              AND e.expenseDate BETWEEN :start AND :end
            """)
    java.math.BigDecimal sumByMemberBetween(@Param("memberId") Long memberId,
                                             @Param("start") LocalDate start,
                                             @Param("end") LocalDate end);

    @Query("""
            SELECT e.category AS category, COALESCE(SUM(e.amount), 0) AS total, COUNT(e) AS count
            FROM Expense e
            GROUP BY e.category
            """)
    List<CategoryTotal> sumByCategory();

    @Query("""
            SELECT e.category AS category, COALESCE(SUM(e.amount), 0) AS total, COUNT(e) AS count
            FROM Expense e
            WHERE e.expenseDate BETWEEN :start AND :end
            GROUP BY e.category
            """)
    List<CategoryTotal> sumByCategoryBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
            SELECT FUNCTION('YEAR', e.expenseDate) AS expenseYear,
                   FUNCTION('MONTH', e.expenseDate) AS expenseMonth,
                   COALESCE(SUM(e.amount), 0) AS total,
                   COUNT(e) AS count
            FROM Expense e
            GROUP BY FUNCTION('YEAR', e.expenseDate), FUNCTION('MONTH', e.expenseDate)
            ORDER BY expenseYear DESC, expenseMonth DESC
            """)
    List<MonthlyTotal> sumByMonth();

    /**
     * Projection interfaces below let Spring Data map aggregate query results
     * straight onto typed accessors instead of returning raw Object[] rows.
     */
    interface CategoryTotal {
        Category getCategory();
        java.math.BigDecimal getTotal();
        long getCount();
    }

    interface MonthlyTotal {
        int getExpenseYear();
        int getExpenseMonth();
        java.math.BigDecimal getTotal();
        long getCount();
    }
}
