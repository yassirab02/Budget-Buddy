import {Component, OnInit} from '@angular/core';
import {UserResponse} from '../../../../services/models/user-response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userResponse:UserResponse = {};
  isLoading = true;

  constructor(private router:Router) {
    const navigation = window.history.state;
    this.userResponse = navigation.user || {};
    this.isLoading = false;
  }

  ngOnInit() {
  }

  getUserInitials(): string {
    if (this.userResponse) {
      const firstName = this.userResponse.firstName || '';
      const lastName = this.userResponse.lastName || '';
      return (firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase());
    }
    return '';
  }

  goToReports() {
    this.router.navigate(['/report']);
  }
}
