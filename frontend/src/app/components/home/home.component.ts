import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isAdmin: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    console.log('HomeComponent initialized');
    try {
      this.isAdmin = this.authService.hasRole('ADMIN');
    } catch (error) {
      console.error('Error in HomeComponent initialization:', error);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
