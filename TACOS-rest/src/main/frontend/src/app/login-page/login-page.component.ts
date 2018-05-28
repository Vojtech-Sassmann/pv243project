import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  public displayError : boolean = false;

  constructor(private authService : AuthService, private router : Router) {}

  ngOnInit() {
  }

  public login(email : String, password : String) {
    let auth = {"email" : email, "password" : password};

    this.authService.authenticate(auth).subscribe(success => {
      console.log(success);
      if (!success) {
        this.displayError = true;
      } else {
        this.router.navigate([("/marketplace")]);
      }
    });
  }
}


