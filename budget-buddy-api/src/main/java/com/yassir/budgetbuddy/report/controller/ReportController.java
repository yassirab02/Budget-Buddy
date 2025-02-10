package com.yassir.budgetbuddy.report.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.report.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
@Tag(name = "Report")
public class ReportController {

    private final ReportService service;


    // Fetch monthly reports for a user
    @GetMapping("/report/{report-id}")
    public ResponseEntity<ReportResponse> getReportById(Authentication connectedUser,@PathVariable("report-id") Integer id) {
        return ResponseEntity.ok(service.getReportById(connectedUser,id));
    }

    // Fetch reports by year for a user
    @GetMapping("/reports/{report-year}")
    public ResponseEntity<List<ReportResponse>> getReportsByYear(Authentication connectedUser,@PathVariable("report-year") Integer year) {
        return ResponseEntity.ok(service.getReportsByYear(connectedUser,year));
    }

    // Fetch monthly reports for a user
    @GetMapping("/all-monthly")
    public ResponseEntity<List<ReportResponse>> getAllMonthlyReports(Authentication connectedUser) {
        return ResponseEntity.ok(service.getAllMonthlyReport(connectedUser));
    }

    // Fetch monthly reports for a user
    @GetMapping("/monthly")
    public ResponseEntity<List<ReportResponse>> getMonthlyReports(Authentication connectedUser) {
        return ResponseEntity.ok(service.getMonthlyReport(connectedUser));
    }

    // Fetch yearly reports for a user
    @GetMapping("/yearly")
    public ResponseEntity<List<ReportResponse>>  getYearlyReports(Authentication connectedUser) {
        return ResponseEntity.ok(service.getYearlyReports(connectedUser));
    }


    // Fetch yearly reports for a user
    @GetMapping("/all-yearly")
    public ResponseEntity<List<ReportResponse>>  getAllYearlyReports(Authentication connectedUser) {
        return ResponseEntity.ok(service.getAllYearlyReports(connectedUser));
    }

    // Get all reports for a user
    @GetMapping("/all-reports")
    public ResponseEntity<PageResponse<ReportResponse>> getAllReports(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllReportsByUser(page, size, connectedUser));
    }

}
