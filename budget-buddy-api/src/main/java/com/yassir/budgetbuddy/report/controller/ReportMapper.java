package com.yassir.budgetbuddy.report.controller;

import com.yassir.budgetbuddy.report.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportMapper {

    public ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .startDate(report.getStartDate())
                .endDate(report.getEndDate())
                .totalIncome(report.getTotalIncome())
                .totalExpenses(report.getTotalExpenses())
                .totalGoalsReached(report.getTotalGoalsReached())
                .balance(report.getBalance())
                .details(report.getDetails())
                .year(report.getYear())
                .month(report.getMonth())
                .mostSpendingMonth(report.getMostSpendingMonth())
                .savingRate(report.getSavingRate())
                .type(report.getType().getName())
                .owner(report.getUser().fullName())
                .build();
    }
}
