import {Component, OnInit} from '@angular/core';
import {ReportService} from '../../../../services/services/report.service';
import {FormGroup} from '@angular/forms';
import {PageResponseWalletResponse} from '../../../../services/models/page-response-wallet-response';
import {ReportResponse} from '../../../../services/models/report-response';
import {Router} from '@angular/router';
import {GetReportById$Params} from '../../../../services/fn/report/get-report-by-id';
import {GetReportsByYear$Params} from '../../../../services/fn/report/get-reports-by-year';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {
  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  monthlyReports: ReportResponse[] = [];
  yearlyReports: ReportResponse[] = [];
  isMonthlyReport: boolean = true; // Default to monthly reports
  years: number[] = [];

  toggleReportView(view: 'monthly' | 'yearly') {
    this.isMonthlyReport = view === 'monthly';
  }


  constructor(private reportService: ReportService, private router: Router) {
  }

  ngOnInit() {
    this.getAllMonthlyReports();
    this.getAllYearlyReports();
    const currentYear = new Date().getFullYear();
    for (let year = 2024; year <= currentYear; year++) {
      this.years.push(year);
    }
  }

  getMonthlyReports() {
    this.reportService.getMonthlyReports().subscribe({
      next: (reports: ReportResponse[]) => {  // The 'reports' is now typed as an array of ReportResponse
        this.monthlyReports = reports;
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'An error occurred while fetching the monthly reports.';
        this.level = 'error';
        this.isLoading = false;
      }
    });
  }

  getYearlyReports() {
    this.reportService.getYearlyReports().subscribe({
      next: (reports: ReportResponse[]) => {  // The 'reports' is now typed as an array of ReportResponse
        this.monthlyReports = reports;
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'An error occurred while fetching the monthly reports.';
        this.level = 'error';
        this.isLoading = false;
      }
    });
  }

  getAllMonthlyReports() {
    this.reportService.getAllMonthlyReports().subscribe({
      next: (reports: ReportResponse[]) => {  // The 'reports' is now typed as an array of ReportResponse
        this.monthlyReports = reports;
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'An error occurred while fetching the yearly reports.';
        this.level = 'error';
        this.isLoading = false;
      }
    });
  }

  getAllYearlyReports() {
    this.reportService.getAllYearlyReports().subscribe({
      next: (reports: ReportResponse[]) => {  // The 'reports' is now typed as an array of ReportResponse
        this.yearlyReports = reports;
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'An error occurred while fetching the yearly reports.';
        this.level = 'error';
        this.isLoading = false;
      }
    });
  }

  getReportsByYear(event: Event) {
    const selectedYear = Number((event.target as HTMLSelectElement).value);
    const params: GetReportsByYear$Params = { 'report-year': selectedYear };

    this.isLoading = true; // Set loading state before the request

    this.reportService.getReportsByYear(params).subscribe({
      next: (reports: ReportResponse[]) => {
        this.monthlyReports = reports.filter((report) => report.type === 'MONTHLY');
        this.yearlyReports = reports.filter((report) => report.type === 'YEARLY');
        this.isLoading = false; // Set loading to false after successful response
      },
      error: (error) => {
        console.error('Error fetching reports:', error);
        this.isLoading = false; // Ensure loading state is cleared on error
      }
    });
  }

  navigateToMonthlyReport(id: number | undefined): void {
    this.router.navigate(['/report/monthly', id]);
  }

  navigateToYearlyReport(id: number | undefined): void {
    this.router.navigate(['/report/yearly', id]);
  }
}
