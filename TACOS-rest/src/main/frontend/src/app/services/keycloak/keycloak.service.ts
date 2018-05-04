import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";

type KeycloakClient = KeycloakModule.KeycloakClient;

@Injectable()
export class KeycloakService {
  static keycloakAuth: KeycloakClient = new Keycloak({
    "url" : environment.keycloakRootUrl,
    "realm": "master",
    "auth-server-url": environment.keycloakRootUrl,
    "ssl-required": "external",
    "resource": "app-angular",
    "public-client": true,
    "confidential-port": 0,
    clientId: "app-angular"
  });

  constructor() { }

  static init(): Promise<any> {
    return new Promise((resolve, reject) => {
      KeycloakService.keycloakAuth.init({onLoad: 'login-required'})
        .success(() => {
          resolve();
        })
        .error((errorData: any) => {
          reject(errorData);
        });
    });
  }

  uthenticated(): boolean {
    return KeycloakService.keycloakAuth.authenticated;
  }

  login() {
    KeycloakService.keycloakAuth.login();
  }

  logout() {
    KeycloakService.keycloakAuth.logout();
  }

  account() {
    KeycloakService.keycloakAuth.accountManagement();
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.keycloakAuth.token) {
        KeycloakService.keycloakAuth
          .updateToken(5)
          .success(() => {
            resolve(<string>KeycloakService.keycloakAuth.token);
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      } else {
        reject('Not loggen in');
      }
    });
  }
}
