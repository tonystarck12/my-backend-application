import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private tokenStorage: TokenStorageService,private router: Router) { }

  ngOnInit(): void {
    if(this.tokenStorage.getToken() == null){
      this.router.navigate(["login"]);
    }
  }

}
