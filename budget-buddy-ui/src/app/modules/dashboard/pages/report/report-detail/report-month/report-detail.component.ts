import {Component, EventEmitter, OnChanges, OnInit, Output, SimpleChanges, ViewChild, ElementRef} from '@angular/core';
import { ReportService } from '../../../../../../services/services/report.service';
import { ReportResponse } from '../../../../../../services/models/report-response';
import { ActivatedRoute } from '@angular/router';
import { GetReportById$Params } from '../../../../../../services/fn/report/get-report-by-id';
import { Chart, registerables } from 'chart.js';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';


Chart.register(...registerables);

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html',
  styleUrls: ['./report-detail.component.css']
})
export class ReportDetailComponent implements OnInit, OnChanges {
  @ViewChild('reportDetailContent', { static: false }) reportDetailContent!: ElementRef;
  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>; // Get canvas reference

  reportResponseDetails: ReportResponse = {};
  isLoading: boolean = true;
  chart: any; // Declare the chart instance

  constructor(
    private reportService: ReportService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const reportId = this.route.snapshot.paramMap.get('id');
    if (reportId) {
      this.findReportById(Number(reportId));
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['totalIncome'] || changes['totalExpense'] || changes['totalDebt']) {
      this.loadChartData();
    }
  }

  findReportById(reportId: number) {
    const params: GetReportById$Params = { 'report-id': reportId };
    this.reportService.getReportById(params).subscribe({
      next: (report: ReportResponse) => {
        this.reportResponseDetails = report;
        this.isLoading = false;

        setTimeout(() => this.loadChartData(), 0); // Ensure canvas exists before drawing
      },
      error: (err) => {
        console.error('Error fetching report details:', err);
        this.isLoading = false;
      }
    });
  }

  loadChartData() {
    if (!this.chartCanvas?.nativeElement) {
      console.error("Canvas element not found!");
      return;
    }

    if (this.chart) {
      this.chart.destroy();
    }

    const income = this.reportResponseDetails.totalIncome ?? 0;
    const expense = this.reportResponseDetails.totalExpenses ?? 0;
    const debt = this.reportResponseDetails.totalDebt ?? 0;

    this.chart = new Chart(this.chartCanvas.nativeElement, {
      type: 'pie',
      data: {
        labels: ['Incomes', 'Expenses', 'Debts'],
        datasets: [
          {
            label: 'Amount',
            data: [income, expense, debt],
            backgroundColor: ['#4CAF50', '#FF5252', '#FFC107'],
            borderColor: ['#4CAF50', '#FF5252', '#FFC107'],
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
            labels: {
              boxWidth: 20,
              padding: 10,
              font: {
                size: 14,
                weight: 'bold',
              },
            },
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem: any) => `$${tooltipItem.raw}`
            }
          }
        }
      }
    });
  }


  exportToPDF() {
      if (!this.reportDetailContent) {
        console.error('reportDetailContent is not available');
        return;
      }

      const data = this.reportDetailContent.nativeElement;

      html2canvas(data, { scale: 2 }).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF('p', 'mm', 'a4');
        const imgWidth = 210;
        const pageHeight = 297;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        let heightLeft = imgHeight;
        let position = 0;

        pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        while (heightLeft > 0) {
          position = heightLeft - imgHeight;
          pdf.addPage();
          pdf.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
          heightLeft -= pageHeight;
        }

        pdf.save('Financial_Report.pdf');
      });
  }
}
