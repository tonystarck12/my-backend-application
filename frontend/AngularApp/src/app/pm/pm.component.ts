import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pm',
  templateUrl: './pm.component.html',
  styleUrls: ['./pm.component.css']
})
export class PmComponent implements OnInit {
  board: string;
  errorMessage: string;

  constructor(private userService: UserService,private tokenStorage: TokenStorageService,private router: Router) { }

  ngOnInit() {
    if(this.tokenStorage.getToken() == null){
      this.router.navigate(["login"]);
    }
    this.userService.getPMBoard().subscribe(
      data => {
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
  }
}
