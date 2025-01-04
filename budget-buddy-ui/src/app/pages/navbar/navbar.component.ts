import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  isMenuOpen = false;

  navigation = [
    { title: 'Home', path: '/home' },
    { title: 'Features', path: '/features' },
    { title: 'Pricing', path: '/pricing' },
    { title: 'About', path: '/about' },
  ];

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

}
