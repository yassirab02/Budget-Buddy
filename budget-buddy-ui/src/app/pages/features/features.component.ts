import { Component } from '@angular/core';

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent {

  features = [
    {
      icon: 'fa-solid fa-chart-column',
      title: 'Track Your Expenses',
      desc: 'Easily track your expenses with detailed categories. Stay on top of your spending habits and manage your finances better.'
    },
    {
      icon: 'fa-solid fa-list-check',
      title: 'Manage Budgets',
      desc: 'Set up and manage budgets for different categories like food, entertainment, and more. Stay within your spending limits effortlessly.'
    },
    {
      icon: 'fa-solid fa-bullseye',
      title: 'Set Financial Goals',
      desc: 'Set personalized financial goals, such as saving for a trip or paying off debt, and track your progress over time.'
    },
    {
      icon: 'fa-solid fa-money-bill-transfer',
      title: 'Money Transfer Simulations',
      desc: 'Simulate transfers between wallets and to other users to get an overview of your finances before making real transactions.'
    },
    {
      icon: 'fa-solid fa-comments',
      title: 'Share Experiences',
      desc: 'Post about your financial journey, share ideas, or tell stories to connect with the BudgetBuddy community and get inspired.'
    },
    {
      icon: 'fa-solid fa-flag-checkered',
      title: 'Add a Goal and Make It Happen',
      desc: 'Create actionable financial goals, break them into smaller milestones, and track your progress to turn your dreams into reality.'
    },
  ];
}
