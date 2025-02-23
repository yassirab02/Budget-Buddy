import { Component, Input, OnChanges, SimpleChanges, ViewChild, ElementRef } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import {Router} from '@angular/router';

Chart.register(...registerables);

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnChanges {
  @Input() totalIncome: number | undefined;
  @Input() totalExpense: number | undefined;
  @Input() totalDebt: number | undefined;
  @ViewChild('chartCanvas') private chartCanvas!: ElementRef;

  chart: any;
  showMessage: boolean = false;

  constructor(private router:Router) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['totalIncome'] || changes['totalExpense'] || changes['totalDebt']) {
      this.loadChartData();
    }
  }

  loadChartData() {
    const income = this.totalIncome ?? 0;
    const expense = this.totalExpense ?? 0;
    const debt = this.totalDebt ?? 0;

    if (income + expense + debt === 0) {
      this.showMessage = true;
      if (this.chart) this.chart.destroy();
      return;
    } else {
      this.showMessage = false;
    }

    if (this.chart) this.chart.destroy();

    const ctx = this.chartCanvas.nativeElement.getContext('2d');
    const gradientColors = this.createGradients(ctx);

    this.chart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Incomes', 'Expenses', 'Debts'],
        datasets: [{
          data: [income, expense, debt],
          backgroundColor: gradientColors,
          borderColor: ['#ffffff'],
          borderWidth: 2,
          hoverOffset: 10,
          hoverBorderColor: '#ffffff'
        }]
      },
      options: {
        cutout: '60%',
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right',
            labels: {
              color: '#4a5568',
              font: {
                size: 14,
                family: 'Inter, sans-serif'
              },
              padding: 20,
              usePointStyle: true,
              pointStyle: 'circle'
            }
          },
          tooltip: {
            backgroundColor: '#2D3748',
            titleFont: { size: 16, family: 'Inter, sans-serif' },
            bodyFont: { size: 14, family: 'Inter, sans-serif' },
            padding: 12,
            displayColors: true,
            callbacks: {
              label: (context) => {
                const label = context.label || '';
                const value = context.parsed || 0;
                return `${label}: $${value.toLocaleString('en-US', { minimumFractionDigits: 2 })}`;
              }
            }
          },
        },
        animation: {
          animateRotate: true,
          animateScale: true
        }
      }
    });
  }

  private createGradients(ctx: CanvasRenderingContext2D): CanvasGradient[] {
    const gradients = [
      ctx.createLinearGradient(0, 0, 0, 200),
      ctx.createLinearGradient(0, 0, 0, 200),
      ctx.createLinearGradient(0, 0, 0, 200)
    ];

    // Income gradient (green)
    gradients[0].addColorStop(0, '#34d399');
    gradients[0].addColorStop(1, '#059669');

    // Expense gradient (red)
    gradients[1].addColorStop(0, '#f87171');
    gradients[1].addColorStop(1, '#dc2626');

    // Debt gradient (orange)
    gradients[2].addColorStop(0, '#fb923c');
    gradients[2].addColorStop(1, '#ea580c');

    return gradients;
  }

  redirectToIncome() {
    this.router.navigate(['/income']);
  }

  redirectToDebt() {
    this.router.navigate(['/debt']);
  }

  redirectToExpense() {
    this.router.navigate(['/expense']);
  }

  redirectToGoal() {
    this.router.navigate(['/goal']);
  }
}
