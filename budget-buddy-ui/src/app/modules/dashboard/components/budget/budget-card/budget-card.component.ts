import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BudgetResponse} from '../../../../../services/models/budget-response';

@Component({
  selector: 'app-budget-card',
  templateUrl: './budget-card.component.html',
  styleUrl: './budget-card.component.css'
})
export class BudgetCardComponent {
  private _budget: BudgetResponse = {};
  private _manage = false;
  private _budgetCover: string | undefined;


  get budgetCover(): string | undefined {
    if (this._budget.budgetCover) {
      return 'data:image/jpg;base64,' + this._budget.budgetCover
    }
    return 'https://source.unsplash.com/user/c_v_r/1900x800';
  }

  get budget(): BudgetResponse {
    return this._budget;
  }

  @Input()
  set budget(value: BudgetResponse) {
    this._budget = value;
  }


  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  @Output() private edit: EventEmitter<BudgetResponse> = new EventEmitter<BudgetResponse>();
  @Output() private details: EventEmitter<BudgetResponse> = new EventEmitter<BudgetResponse>();

  onEdit() {
    this.edit.emit(this._budget);
  }

  onShowDetails() {
    this.details.emit(this._budget);
  }
}
