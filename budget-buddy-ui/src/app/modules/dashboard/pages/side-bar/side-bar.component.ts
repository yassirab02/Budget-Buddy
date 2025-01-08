import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements OnInit{
  isSidebarVisible: boolean = true;


  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible;
  }

  constructor(private router: Router) {}


  ngOnInit(): void {
  }

}
