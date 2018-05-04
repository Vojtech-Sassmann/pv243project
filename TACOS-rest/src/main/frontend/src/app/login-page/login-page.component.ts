import { Component, OnInit } from '@angular/core';
import {KeycloakService} from "../services/keycloak/keycloak.service";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private keycloakService : KeycloakService) {}

  ngOnInit() {
  }

  public login() : void {
    console.log(this.keycloakService.account());
  }
}


