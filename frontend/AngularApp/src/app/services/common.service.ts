import { Injectable } from '@angular/core';
import { TokenStorageService } from '../auth/token-storage.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  loggedOut:boolean = false;
  constructor(private tokenStorage: TokenStorageService,private router: Router) { }


  isJWTTokenAvailable(){
    if(this.tokenStorage.getToken() == null){
      this.router.navigate(["login"]);
    }
  }
}
