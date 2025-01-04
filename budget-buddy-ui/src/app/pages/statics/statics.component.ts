import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-statics',
  templateUrl: './statics.component.html',
  styleUrls: ['./statics.component.css']
})
export class StaticsComponent implements OnInit {

  stats = [
    { data: '10K+', title: 'Budgets Created' },
    { data: '50M+', title: 'Expenses Tracked' },
    { data: '100K+', title: 'Financial Goals Set' }
  ];

  constructor() {}

  ngOnInit(): void {}
}
