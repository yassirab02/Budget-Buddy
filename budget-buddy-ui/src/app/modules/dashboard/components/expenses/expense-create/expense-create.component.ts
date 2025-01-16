import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ExpensesResponse} from '../../../../../services/models/expenses-response';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ExpensesRequest} from '../../../../../services/models/expenses-request';
import {MatDialogRef} from '@angular/material/dialog';
import {format} from 'node:url';
import {formatDate} from '@angular/common';

@Component({
  selector: 'app-expense-create',
  templateUrl: './expense-create.component.html',
  styleUrl: './expense-create.component.css'
})
export class ExpenseCreateComponent implements OnInit{
  expenseForm: FormGroup;
  expenseTypes = ['Fixed', 'Variable'];
  date: Date | null = null;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ExpenseCreateComponent>
  ) {
    this.expenseForm = this.fb.group({
      name: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      description: [''],
      date: [''],
      expenseType: ['Variable'],
      categoryId: ['', Validators.required],
      budgetId: ['', Validators.required],
      walletId: [''],
    });
  }

  ngOnInit() {
  }

  formatDate(date: Date | null): string {
    return date ? this.formatDate(date) : 'Pick a date';
  }

  onDateChange(date: Date): void {
    this.date = date;
    this.expenseForm.patchValue({ date });
  }

  onSubmit(): void {
    if (this.expenseForm.valid) {
      const formValues = this.expenseForm.value;
      console.log({
        ...formValues,
        amount: parseFloat(formValues.amount),
        categoryId: parseInt(formValues.categoryId, 10),
        budgetId: parseInt(formValues.budgetId, 10),
        walletId: formValues.walletId ? parseInt(formValues.walletId, 10) : undefined,
      });
      this.dialogRef.close();
    } else {
      this.markFormGroupTouched(this.expenseForm);
    }
  }

  markFormGroupTouched(group: FormGroup): void {
    Object.values(group.controls).forEach((control) => {
      control.markAsTouched();
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  protected readonly Date = Date;
}
