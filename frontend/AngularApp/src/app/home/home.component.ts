import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  info: any;
  roles: string[];
  authority: string;
  lastLogin: string;
  showLogoutModal: boolean = false;
  userRole: any [] = [
    {
      "link": "dashboard",
      "name" : "Dashboard",
      "display": true,
      "dropdown" : false
    },
    {
      "link": "user",
      "name" : "User Board",
      "display": true,
      "dropdown" : true
    }
  ]
  adminRole:any [] = [
    {
      "link": "dashboard",
      "name" : "Dashboard",
      "dropdown" : false
    },
    {
      "link": "user",
      "name" : "User Board",
      "dropdown" : false
    }
    ,
    {
      "link": "admin",
      "name" : "Admin Board",
      "dropdown" : true
    }
    ,
    {
      "link": "pm",
      "name" : "PM Board",
      "dropdown" : false
    }

  ];

  pmRole: any[] = [
    {
      "link": "dashboard",
      "name" : "Dashboard",
      "dropdown" : false
    },
    {
      "link": "user",
      "name" : "User Board",
      "dropdown" : true
    }
     ,
    {
      "link": "pm",
      "name" : "PM Board",
      "dropdown" : false
    }
  ];
  navigationMenu: any[] = [];

  constructor(private commonService: CommonService,private tokenStorage: TokenStorageService,private router: Router) { 
    this.commonService.isJWTTokenAvailable();
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.lastLogin = this.tokenStorage.getLastLogin();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          this.navigationMenu = this.adminRole;
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          this.navigationMenu = this.pmRole;
          return false;
        }
        this.authority = 'user';
        this.navigationMenu = this.userRole;
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
    this.commonService.loggedOut = true;
    this.tokenStorage.signOut();
    this.router.navigate(["login"]);
  }

  displayLogoutConfirmation() {
   this.showLogoutModal = true;
  }
}
