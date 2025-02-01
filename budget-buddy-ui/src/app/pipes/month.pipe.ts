import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'month'
})
export class MonthPipe implements PipeTransform {
  private months: string[] = [
    'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'
  ];

  transform(value: number | null | undefined): string {
    if (!value || value < 1 || value > 12) return 'Unknown'; // Handle invalid cases
    return this.months[value - 1]; // Convert 1-based index to 0-based index
  }
}
