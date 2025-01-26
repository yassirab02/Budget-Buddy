import {Component, HostListener, Input, OnInit} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements OnInit{
  isSidebarVisible: boolean = false;


  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible;
  }

  constructor(private router: Router) {}


  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      // Close the sidebar on route change (for mobile)
      this.isSidebarVisible = false;
    });
  }

}
