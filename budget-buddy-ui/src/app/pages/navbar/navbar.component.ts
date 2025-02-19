import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  isMenuOpen = false;

  constructor(private router: Router) {}

  isActive(path: string): boolean {
    return this.router.isActive(path, true);
  }
  navigation = [
    { title: 'Home', path: '/home' },
    { title: 'Features', path: '/features' },
    { title: 'How it works', path: '/works' },
    // { title: 'Contact', path: '/contact' },
  ];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

}
