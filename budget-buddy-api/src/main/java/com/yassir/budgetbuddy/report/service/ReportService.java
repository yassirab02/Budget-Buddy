package com.yassir.budgetbuddy.report.service;


import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.report.controller.ReportResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface ReportService {

    PageResponse<ReportResponse> findAllReportsByUser(int page, int size, Authentication connectedUser);

    List<ReportResponse> getMonthlyReport(Authentication connectedUser);

    List<ReportResponse> getAllMonthlyReport(Authentication connectedUser);

    List<ReportResponse> getYearlyReports(Authentication connectedUser);

    // Scheduled method to generate monthly reports (runs on the 1st day of each month at midnight)
    @Scheduled(cron = "0 0 0 1 * *")
    // Cron expression for 1st of every month at 00:00 AM
    void generateMonthlyReport();

    // Scheduled method to generate yearly reports (runs on the 1st of January at midnight)
    @Scheduled(cron = "0 0 0 1 1 *")
    // Cron expression for 1st of January every year at midnight (00:00 AM)
    void generateYearlyReports();


    List<ReportResponse> getAllYearlyReports(Authentication connectedUser);

    ReportResponse getReportById(Authentication connectedUser, Integer id);
}
