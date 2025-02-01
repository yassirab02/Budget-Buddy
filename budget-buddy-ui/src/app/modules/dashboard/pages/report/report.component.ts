import {Component, OnInit} from '@angular/core';
import {ReportService} from '../../../../services/services/report.service';
import {FormGroup} from '@angular/forms';
import {PageResponseWalletResponse} from '../../../../services/models/page-response-wallet-response';
import {ReportResponse} from '../../../../services/models/report-response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit  {
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

  toggleReportView(view: 'monthly' | 'yearly') {
    this.isMonthlyReport = view === 'monthly';
  }


constructor(private reportService: ReportService,private router: Router) {
  }
    ngOnInit() {
    this.getAllMonthlyReports();
    this.getAllYearlyReports();
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

  navigateToMonthlyReport(id: number|undefined): void {
    this.router.navigate(['/report/monthly', id]);
  }

  navigateToYearlyReport(id: number|undefined): void {
    this.router.navigate(['/report/yearly', id]);
  }
}
