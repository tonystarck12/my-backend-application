import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  info: any;
  roles: string[];
  authority: string;
  showLogoutModal: boolean = false;
  constructor(private tokenStorage: TokenStorageService,private router: Router) { }

  ngOnInit() {
    window.addEventListener('storage', (event) => {
      const credentials = JSON.parse(window.sessionStorage.getItem('CREDENTIALS_TOKEN'))
      if(event.key === 'REQUESTING_SHARED_CREDENTIALS' && credentials) {
        window.localStorage.setItem('CREDENTIALS_SHARING', JSON.stringify({ token: 'any-token-you-want' }))
        window.localStorage.removeItem('CREDENTIALS_SHARING')
      }
      if(event.key === 'CREDENTIALS_SHARING' && !credentials){
        window.sessionStorage.setItem('CREDENTIALS_TOKEN', event.newValue)
      }
    })
    window.localStorage.setItem('REQUESTING_SHARED_CREDENTIALS', Date.now().toString())
    window.localStorage.removeItem('REQUESTING_SHARED_CREDENTIALS')
    if(this.tokenStorage.getToken() == null){
      this.router.navigate(["auth/login"]);
    }
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          return false;
        }
        this.authority = 'user';
        return true;
      });
      this.router.navigate(["home/dashboard"]);
    }
    this.info = {
      token: this.tokenStorage.getToken(),
      username: this.tokenStorage.getUsername(),
      authorities: this.tokenStorage.getAuthorities()
    };
  }

  logout(){
    this.tokenStorage.signOut();
    this.router.navigate(["auth/login"]);
  }

  displayLogoutConfirmation() {
   this.showLogoutModal = true;
  }
}
