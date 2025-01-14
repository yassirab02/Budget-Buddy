import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BudgetRequest} from '../../../../../services/models/budget-request';
import {BudgetService} from '../../../../../services/services/budget.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-budget-create',
  templateUrl: './budget-create.component.html',
  styleUrl: './budget-create.component.css'
})
export class BudgetCreateComponent implements OnInit {
  @Output() closeModal = new EventEmitter<void>();
  @Output() budgetCreated = new EventEmitter<void>(); // Emits an event when a new budget is created

  showSuccess=false
  errorMsg: Array<string> = [];
  budgetRequest: BudgetRequest = {
    name: '',
    amount: 0,
    description: '',
    limitAmount: 0,
    targetAmount: 0
  };


  budgetForm: FormGroup;
  isSubmitting = false;

  constructor(private fb: FormBuilder,
              private router: Router,
              private budgetService : BudgetService,
              private activatedRoute: ActivatedRoute,) {
    this.budgetForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      amount: [0, [Validators.required]],
      targetAmount: [0, [Validators.required]],
      limitAmount: [0, [Validators.required]]
    });
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
        }
      });
    }
  }

  saveBudget() {
    this.budgetService.addOrUpdateBudget({
      body: this.budgetRequest
    }).subscribe({
      next: (budgetId) => {
        // Set isAdd to false after successful save
        this.closeModal.emit();
        this.budgetForm.reset();
        this.showSuccess = true;
        this.budgetCreated.emit(); // Emit the event to notify parent component
        setTimeout(() => {
          this.showSuccess = false;
        }, 5000);
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }


  closeError(): void {
    this.errorMsg = []; // Clear the error messages
  }

  closeCreate() {
    this.closeModal.emit();
  }
}
