import {Component, OnInit} from '@angular/core';
import {BudgetRequest} from '../../../../../services/models/budget-request';
import {BudgetService} from '../../../../../services/services/budget.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-budget-create',
  templateUrl: './budget-create.component.html',
  styleUrl: './budget-create.component.css'
})
export class BudgetCreateComponent implements OnInit{
  errorMsg: Array<string> = [];

  budgetRequest: BudgetRequest = {
    name: '',
    amount: 0,
    description: '',
    limitAmount: 0,
    targetAmount: 0
  };
  selectedBudgetCover: any;
  selectedPicture: string | undefined;

  constructor(
    private router: Router,
    private budgetService : BudgetService,
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit() {
    const budgetId = this.activatedRoute.snapshot.params['budgetId'];
    if (budgetId) {
      this.budgetService.findBudgetById({
        'budget-id': budgetId
      }).subscribe({
        next: (budget) => {
          this.budgetRequest = {
            id: budget.id,
            name: budget.name as string,
            description: budget.description as string,
            amount: budget.amount as number,
            targetAmount: budget.targetAmount as number,
            limitAmount: budget.limitAmount as number
          };
          this.selectedPicture = 'data:image/jpg;base64,' + budget.budgetCover;
        }
      });
    }
  }

    saveBudget() {
      this.budgetService.addOrUpdateBudget({
        body: this.budgetRequest
      }).subscribe({
        next: (budgetId) => {
          this.budgetService.uploadBudgetCoverPicture({
            'budget-id': budgetId,
            body: {
              file: this.selectedBudgetCover
            }
          }).subscribe({
            next: () => {
              this.router.navigate(['/budget']);
            }
          });
        },
        error: (err) => {
          console.log(err.error);
          this.errorMsg = err.error.validationErrors;
        }
      });
    }

  onFileSelected(event: any) {
    this.selectedBudgetCover = event.target.files[0];
    console.log(this.selectedBudgetCover);
    if (this.selectedBudgetCover) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedBudgetCover);
    }
  }

  closeError(): void {
    this.errorMsg = []; // Clear the error messages
  }
}
