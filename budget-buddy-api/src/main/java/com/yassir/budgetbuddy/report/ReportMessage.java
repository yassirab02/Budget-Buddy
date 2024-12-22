package com.yassir.budgetbuddy.report;

import lombok.Getter;

@Getter
public enum ReportMessage {

    // Monthly feedback messages
    MONTHLY_INCOME_MESSAGE("Your total income for this month is: "),
    MONTHLY_EXPENSES_MESSAGE("Your total expenses for this month are: "),
    MONTHLY_BALANCE_MESSAGE("Your remaining balance for this month is: "),
    MONTHLY_BUDGET_OVERSHOOT_MESSAGE("You spent %d%% more than budgeted on %s this month. Consider reviewing your spending in this category."),
    MONTHLY_BUDGET_UNDERSHOOT_MESSAGE("You saved %d%% more than budgeted on %s this month. Great job keeping your expenses low!"),
    MONTHLY_EXPENSE_INCREASE_MESSAGE("Your expenses increased by %d%% this month compared to last month. Look for areas to cut back."),
    MONTHLY_SAVINGS_MESSAGE("You saved %d%% of your income this month. Keep up the good work to reach your savings goals!"),

    // Yearly feedback messages
    YEARLY_INCOME_MESSAGE("Your total income for the year is: "),
    YEARLY_EXPENSES_MESSAGE("Your total expenses for the year are: "),
    YEARLY_BALANCE_MESSAGE("Your remaining balance for the year is: "),
    YEARLY_BUDGET_OVERSHOOT_MESSAGE("You spent %d%% more than budgeted on %s this year. Consider adjusting your budget for next year."),
    YEARLY_BUDGET_UNDERSHOOT_MESSAGE("You saved %d%% more than budgeted on %s this year. Excellent job!"),
    YEARLY_EXPENSE_INCREASE_MESSAGE("Your expenses have increased by %d%% compared to last year. Try to identify the reasons for this increase."),
    YEARLY_SAVINGS_MESSAGE("You saved %d%% of your income this year. Keep going to achieve your long-term financial goals!"),
    MOST_SPENDING_MONTH_MESSAGE("Your most spending month this year was %s. Consider reviewing your expenses during this period."),

    // General recommendations
    SAVINGS_GOAL_MESSAGE("Consider saving more in the upcoming months to reach your financial goal. Small changes can make a big difference."),
    SPENDING_WARNING_MESSAGE("You have exceeded your budget in multiple categories this month. Review your spending habits to avoid overspending."),
    DEBT_ALERT_MESSAGE("You are spending more than your income in recent months. Try to adjust your expenses to avoid accumulating debt."),
    FINANCIAL_DISCIPLINE_MESSAGE("Maintaining financial discipline is key. Set small goals and track your progress to stay on track."),

    // Status messages
    REPORT_GENERATED_MESSAGE("The report for this period has been generated successfully."),
    NO_INCOME_MESSAGE("No income was recorded in this period."),
    NO_EXPENSES_MESSAGE("No expenses were recorded in this period."),
    NO_BUDGET_MESSAGE("No budgets were set up for the selected period."),
    REPORT_GENERATION_FAILED("Failed to generate the report. Please try again later."),
    INVALID_DATE_RANGE_MESSAGE("The date range you selected is invalid. Please choose a valid start and end date."),

    // Debt-related feedback messages
    MONTHLY_TOTAL_DEBT_MESSAGE("Your total debt this month is: "),
    MONTHLY_TOTAL_PAID_DEBT_MESSAGE("You successfully paid %s of your debts this month. Well done!"),
    MONTHLY_TOTAL_UNPAID_DEBT_MESSAGE("Your remaining unpaid debts total %s. Consider planning your payments to avoid overdue penalties."),
    MONTHLY_DEBT_ALERT_MESSAGE("Your unpaid debts are high compared to your income. Adjust your budget to prioritize debt repayment."),
    YEARLY_TOTAL_DEBT_MESSAGE("Your total debt this year was: "),
    YEARLY_DEBT_REDUCTION_MESSAGE("You reduced your debts by %s this year. Keep working towards financial freedom!"),
    YEARLY_TOTAL_PAID_DEBT_MESSAGE("Your total paid debt for this year is: "),
    YEARLY_TOTAL_UNPAID_DEBT_MESSAGE("Your total unpaid debt for this year is: "),

    ;

    private final String message;

    ReportMessage(String message) {
        this.message = message;
    }
}
