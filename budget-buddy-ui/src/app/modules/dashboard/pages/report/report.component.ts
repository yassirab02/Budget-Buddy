import {Component, OnInit} from '@angular/core';
import {ReportService} from '../../../../services/services/report.service';
import {FormGroup} from '@angular/forms';
import {PageResponseWalletResponse} from '../../../../services/models/page-response-wallet-response';
import {ReportResponse} from '../../../../services/models/report-response';

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
  constructor(private reportService: ReportService) {
  }
    ngOnInit() {
    this.getMonthlyReports();
    this.getYearlyReports();
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

}
