package com.yassir.budgetbuddy.report.repository;

import com.yassir.budgetbuddy.report.Report;
import com.yassir.budgetbuddy.report.ReportType;
import com.yassir.budgetbuddy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> , JpaSpecificationExecutor<Report> {


    @Query("SELECT r FROM Report r WHERE r.user.id = :userId AND r.type = :type AND " +
            "MONTH(r.startDate) = MONTH(CURRENT_DATE) AND YEAR(r.startDate) = YEAR(CURRENT_DATE)")
    List<Report> findByUserIdAndTypeAndCurrentMonth(@Param("userId") Integer userId, @Param("type") ReportType type);

    @Query("SELECT r FROM Report r WHERE r.user.id = :userId AND r.type = :type AND " +
            "YEAR(r.startDate) = YEAR(CURRENT_DATE)")
    List<Report> findByUserIdAndTypeAndCurrentYear(@Param("userId") Integer userId, @Param("type") ReportType type);


    Optional<Report> findByUserAndYearAndMonth(User user, Integer year, Integer month);


}
