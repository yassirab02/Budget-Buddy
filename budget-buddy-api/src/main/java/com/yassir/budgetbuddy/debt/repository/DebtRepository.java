package com.yassir.budgetbuddy.debt.repository;

import com.yassir.budgetbuddy.debt.Debt;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface DebtRepository extends JpaRepository<Debt, Integer>, JpaSpecificationExecutor<Debt> {

    List<Debt> findByOwnerAndIssueDateBefore(User owner, LocalDate endDate);

    List<Debt> findByOwnerAndIsPaidTrueAndIssueDateBefore(User owner, LocalDate endDate);

    @Query("SELECT d FROM Debt d WHERE d.owner = :user AND YEAR(d.issueDate) = :year")
    List<Debt> findByOwnerAndYear(@Param("user") User user, @Param("year") int year);

    @Query("SELECT d FROM Debt d WHERE d.owner = :user AND YEAR(d.issueDate) = :year AND d.isPaid = :isPaid")
    List<Debt> findByOwnerAndYearAndIsPaid(@Param("user") User user, @Param("year") int year, @Param("isPaid") boolean isPaid);

    @Query("""
             SELECT debt
             FROM Debt debt
             WHERE debt.owner.id = :ownerId
             AND debt.isPaid = false
            """)
    Page<Debt> findAllNonPaidDebtsByOwner(Integer ownerId, Pageable pageable);

    @Query("""
                SELECT debt
                FROM Debt debt
                WHERE debt.owner.id = :ownerId
                AND debt.isPaid = true
            """)
    Page<Debt> findAllPaidDebtsByOwner(Integer ownerId, Pageable pageable);

    @Query("""
                SELECT debt
                FROM Debt debt
                WHERE debt.owner.id = :ownerId
                AND debt.isPaid = :paid
            """)
    Page<Debt> findDebtsByOwnerAndPaidStatus(Integer ownerId, boolean paid, Pageable pageable);

    @Query("""
                SELECT debt
                FROM Debt debt
                WHERE debt.owner.id = :ownerId
            """)
    List<Debt> findDebtsByOwnerId(Integer id);
}
