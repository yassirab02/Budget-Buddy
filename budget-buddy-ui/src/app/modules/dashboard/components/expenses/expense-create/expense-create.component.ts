import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ExpensesResponse} from '../../../../../services/models/expenses-response';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExpensesRequest} from '../../../../../services/models/expenses-request';

@Component({
  selector: 'app-expense-create',
  templateUrl: './expense-create.component.html',
  styleUrl: './expense-create.component.css'
})
export class ExpenseCreateComponent implements OnInit{
  @Output() onSubmit = new EventEmitter<ExpensesResponse>();

  expensesForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.expensesForm = this.fb.group({
      name: ['', [Validators.required]],
      amount: [0, [Validators.required, Validators.min(0.01)]],
      description: [''],
      date: [this.getCurrentDate(), [Validators.required]],
      expensesType: [, [Validators.required]],
      categoryId: [0, [Validators.required, Validators.min(1)]],
      budgetId: [0, [Validators.required, Validators.min(1)]],
      walletId: [null]
    });
  }

  ngOnInit(): void {}

  getCurrentDate(): string {
    return new Date().toISOString().split('T')[0];
  }

  handleSubmit(): void {
    if (this.expensesForm.valid) {
      const formData: ExpensesRequest = this.expensesForm.value;
      const simulatedResponse: ExpensesResponse = {
        id: Math.floor(Math.random() * 1000),
        ...formData,
        category: 'Simulated Category',
        budget: 'Simulated Budget',
      };
      this.onSubmit.emit(simulatedResponse);
      this.expensesForm.reset({
        name: '',
        amount: 0,
        description: '',
        date: this.getCurrentDate(),
        expensesType: '',
        categoryId: 0,
        budgetId: 0,
        walletId: null
      });
    }
  }
}
