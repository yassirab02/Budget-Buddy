import {Component, OnInit} from '@angular/core';
import {PageResponseGoalResponse} from '../../../../services/models/page-response-goal-response';
import {GoalService} from '../../../../services/services/goal.service';

@Component({
  selector: 'app-goal',
  templateUrl: './goal.component.html',
  styleUrl: './goal.component.css'
})
export class GoalComponent implements OnInit {

  isLoading: boolean = true;
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  createGoal = false;
  goalResponse: PageResponseGoalResponse = {};  // Store the actual wallet
  showSuccess=false;
  showReached = true;


  constructor(
    private goalService: GoalService,
  ) {
  }

  ngOnInit() {
    this.findAllGoals();
  }

  findAllGoals(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.goalService.findAllGoalsByUser({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (goals) => {
          this.goalResponse = goals;
          this.pages = Array(this.goalResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching goals:', err);
          this.message = 'An error occurred while fetching the goals.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  get totalProgress(): number {
    const totalCurrent = this.goalResponse.content?.reduce((sum, goal) => sum + (goal.currentAmount || 0), 0) || 0;
    const totalTarget = this.goalResponse.content?.reduce((sum, goal) => sum + (goal.targetAmount || 0), 0) || 0;
    return totalTarget > 0 ? (totalCurrent / totalTarget) * 100 : 0;
  }

  toggleShowReached(event: any): void {
    this.showReached = event.target.checked;
  }

  get filteredGoals() {
    return this.goalResponse.content?.filter(goal =>
      this.showReached || !goal.reached // Show all if showReached is true, else show only not reached goals
    );
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllGoals();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllGoals();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllGoals();
  }

  goToLastPage() {
    this.page = this.goalResponse.totalPages as number - 1;
    this.findAllGoals();
  }

  goToNextPage() {
    this.page++;
    this.findAllGoals();
  }

  get isLastPage() {
    return this.page === this.goalResponse.totalPages as number - 1;
  }

}
