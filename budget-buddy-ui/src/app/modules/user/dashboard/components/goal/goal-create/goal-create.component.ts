import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GoalRequest} from '../../../../../../services/models/goal-request';
import {GoalService} from '../../../../../../services/services/goal.service';

@Component({
  selector: 'app-goal-create',
  templateUrl: './goal-create.component.html',
  styleUrl: './goal-create.component.css'
})
export class GoalCreateComponent {
  @Output() closeModal = new EventEmitter<void>();
  @Output() goalCreated = new EventEmitter<void>(); // Emits an event when a new budget is created
  @Output() showSuccess = new EventEmitter<unknown>();

  goalForm: FormGroup;
  errorMsg: Array<string> = [];
  goalRequest: GoalRequest = {
    name: '',
    currentAmount: 0,
    targetAmount: 0,
    startDate: '',
    targetDate: '',
    description: '',
    reached: false,
  };

  constructor(
    private fb: FormBuilder,
    private goalService: GoalService,
  ) {
    this.goalForm = this.fb.group({
      name: ['', Validators.required],
      currentAmount: ['', [Validators.required, Validators.min(0.01)]],
      targetAmount: ['', [Validators.required, Validators.min(0.01)]],
      description: [''],
      startDate: [''],
      targetDate: [''],
      reached: [false],
    });
  }


  saveGoal() {
    this.goalService.addOrUpdateGoal({
      body: this.goalRequest
    }).subscribe({
      next: (goalId) => {
        // Set isAdd to false after successful save
        this.closeModal.emit();
        this.goalForm.reset();
        this.showSuccess.emit();
        this.goalCreated.emit(); // Emit the event to notify parent component
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  close() {
    this.closeModal.emit();
  }

  closeError(): void {
    this.errorMsg = []; // Clear the error messages
  }
}
