import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Chart, registerables } from 'chart.js';

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

  chart: any; // Declare the chart instance

  constructor() {}

  ngOnChanges(changes: SimpleChanges): void {
    // Whenever the inputs change, update the chart
    if (changes['totalIncome'] || changes['totalExpense'] || changes['totalDebt']) {
      this.loadChartData();
    }
  }

  loadChartData() {
    if (this.chart) {
      this.chart.destroy(); // Destroy the previous chart if it exists
    }

    const income = this.totalIncome ?? 0; // Use 0 if undefined
    const expense = this.totalExpense ?? 0; // Use 0 if undefined
    const debt = this.totalDebt ?? 0; // Use 0 if undefined

    this.chart = new Chart('dashboardChart', {
      type: 'pie', // Change chart type to 'pie'
      data: {
        labels: ['Incomes', 'Expenses', 'Debts'],
        datasets: [
          {
            label: 'Amount',
            data: [income, expense, debt],
            backgroundColor: ['#4CAF50', '#FF5252', '#FFC107'], // Green for income, red for expense, yellow for debt
            borderColor: ['#4CAF50', '#FF5252', '#FFC107'], // Same color for borders
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top', // Position the legend on top
            labels: {
              boxWidth: 20, // Make legend box smaller
              padding: 10,  // Add space between legend items
              font: {
                size: 14, // Change the font size of the legend
                weight: 'bold', // Set bold font for the legend
              },
            },
          },
          tooltip: {
            callbacks: {
              label: function(tooltipItem: any) {
                return `$${tooltipItem.raw}`; // Format the tooltip values as currency
              }
            }
          }
        }
      }
    });
  }
}
