import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  board: string;
  errorMessage: string;

  constructor(private userService: UserService,private commonService: CommonService) {
    this.commonService.isJWTTokenAvailable();
   }

  ngOnInit() {
    this.userService.getAdminBoard().subscribe(
      data => {
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
  }
}
