package com.yassir.budgetbuddy.report.controller;

import com.yassir.budgetbuddy.report.Report;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportMapper {

    public ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .totalIncome(report.getTotalIncome()!= null ? report.getTotalIncome() : BigDecimal.valueOf(0))
                .totalExpenses(report.getTotalExpenses()!= null ? report.getTotalExpenses() : BigDecimal.valueOf(0))
                .totalGoalsReached(report.getTotalGoalsReached() != null ? report.getTotalGoalsReached() : 0)
                .totalGoals(report.getTotalGoals()!= null ? report.getTotalGoals() : 0)
                .totalDebt(report.getTotalDebt()!= null ? report.getTotalDebt() : BigDecimal.valueOf(0))
                .totalPaidDebt(report.getTotalPaidDebt()!= null ? report.getTotalPaidDebt() : BigDecimal.valueOf(0))
                .balance(report.getBalance()!= null ? report.getBalance() : BigDecimal.valueOf(0))
                .details(report.getDetails())
                .year(report.getYear())
                .month(report.getMonth())
                .mostSpendingMonth(report.getMostSpendingMonth() != null ? report.getMostSpendingMonth() : 0)
                .savingRate(report.getSavingRate()!= null ? report.getSavingRate() : BigDecimal.valueOf(0))
                .type(report.getType().getName())
                .owner(report.getUser().fullName())
                .build();
    }

    public List<ReportResponse> toReportResponseList(List<Report> reports) {
        return reports.stream()
                .map(this::toReportResponse)  // Use the single Report to ReportResponse method
                .collect(Collectors.toList());
    }

}
