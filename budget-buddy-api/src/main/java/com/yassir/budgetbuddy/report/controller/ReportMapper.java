package com.yassir.budgetbuddy.report.controller;

import com.yassir.budgetbuddy.report.Report;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportMapper {

    public ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .totalIncome(report.getTotalIncome())
                .totalExpenses(report.getTotalExpenses())
                .totalGoalsReached(report.getTotalGoalsReached() != null ? report.getTotalGoalsReached() : 0)
                .totalGoals(report.getTotalGoals())
                .totalDebt(report.getTotalDebt())
                .totalPaidDebt(report.getTotalPaidDebt())
                .balance(report.getBalance())
                .details(report.getDetails())
                .year(report.getYear())
                .month(report.getMonth())
                .mostSpendingMonth(report.getMostSpendingMonth() != null ? report.getMostSpendingMonth() : 0)
                .savingRate(report.getSavingRate())
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
