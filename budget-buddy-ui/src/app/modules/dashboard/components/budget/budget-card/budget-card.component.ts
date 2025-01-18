import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BudgetService} from '../../../../../services/services/budget.service';
import {DeleteBudget1$Params} from '../../../../../services/fn/budget/delete-budget-1';

@Component({
  selector: 'app-budget-card',
  templateUrl: './budget-card.component.html',
  styleUrls: ['./budget-card.component.css'],
})
export class BudgetCardComponent {
  @Input() id!: number | undefined;
  @Input() name!: string;
  @Input() description!: string;
  @Input() amount: number | undefined = 0; // Default to 0 if undefined
  @Input() targetAmount: number | undefined = 0; // Default to 0 if undefined
  @Input() limitAmount: number | undefined = 0; // Default to 0 if undefined
  @Output() refreshBudget = new EventEmitter<number>();

  isDelete=false;


  get progress(): number {
    return this.targetAmount ? (this.amount! / this.targetAmount!) * 100 : 0; // Check targetAmount is defined
  }

  constructor(
    private budgetService: BudgetService,
  ) {
  }

  deleteBudget(id: number | undefined) {
    if (id === undefined || id === null) {
      console.error('Invalid ID, cannot delete budget');
      return;
    }

    // Create the params object with the 'budget-id' property
    const params: DeleteBudget1$Params = { 'budget-id': id };

    // Call the deleteBudget1 method from the service
    this.budgetService.deleteBudget1(params).subscribe({
      next: () => {
        console.log('Budget deleted successfully');
        this.isDelete=false;
        this.refreshBudget.emit(this.id);
      },
      error: (err) => {
        console.error('Error deleting budget:', err);
      }
    });
  }

}
