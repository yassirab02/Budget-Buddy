import {AfterViewInit, Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'budget-buddy-ui';
  isLoading: boolean = false;

  ngOnInit() {}

}
