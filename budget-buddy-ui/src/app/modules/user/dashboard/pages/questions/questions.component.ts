import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrl: './questions.component.css'
})
export class QuestionsComponent implements OnInit{
  constructor( private router: Router,
  ) {
  }
  ngOnInit() {
  }

  dashboard() {
    this.router.navigate(['dashboard']);
  }
}
