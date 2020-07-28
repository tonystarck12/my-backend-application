import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import { Router } from '@angular/router';
import { NgxSpinnerService } from "ngx-spinner";
import { CommonService } from '../services/common.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loggedOut : boolean = this.commonService.loggedOut;
  private loginInfo: AuthLoginInfo;

  constructor(private commonService: CommonService,private spinnerService: NgxSpinnerService,private formBuilder: FormBuilder,private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }

  

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
  });
  
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.loggedOut = false;
    this.spinnerService.show();
    this.submitted = true;
    console.log("On Submit Form>>",this.loginForm);
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      this.spinnerService.hide();
      return;
    }

    this.loginInfo = new AuthLoginInfo(
      this.loginForm.get('username').value,
      this.loginForm.get('password').value);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.spinnerService.hide();
        this.router.navigate(["home"]);
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
        this.spinnerService.hide();
      }
    );
  }

}
