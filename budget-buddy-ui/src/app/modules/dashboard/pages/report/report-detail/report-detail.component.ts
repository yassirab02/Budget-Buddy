import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ReportService } from '../../../../../services/services/report.service';
import { ReportResponse } from '../../../../../services/models/report-response';
import { ActivatedRoute } from '@angular/router';
import { GetReportById$Params } from '../../../../../services/fn/report/get-report-by-id';

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html',
  styleUrls: ['./report-detail.component.css'] // Fixed the typo here (it should be `styleUrls` not `styleUrl`)
})
export class ReportDetailComponent implements OnInit {
  @Output() onClose = new EventEmitter<unknown>();

  reportResponseDetails: ReportResponse = {};
  isLoading: boolean = true;

  constructor(
    private reportService: ReportService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const reportId = this.route.snapshot.paramMap.get('id'); // Get report ID from route params
    if (reportId) {
      this.findReportById(Number(reportId));
    }
  }

  // Fetch the report by ID
  findReportById(reportId: number) {
    const params: GetReportById$Params = { 'report-id': reportId };
    this.reportService.getReportById(params).subscribe({
      next: (report: ReportResponse) => {
        this.reportResponseDetails = report;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching report details:', err);
        this.isLoading = false;
      }
    });
  }

  // Emit the close event to the parent component
  closeDetails() {
    this.onClose.emit();
  }
}
