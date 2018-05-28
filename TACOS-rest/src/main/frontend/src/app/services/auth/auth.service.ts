import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth";

  constructor(protected httpClient:HttpClient) { }

  public authenticate(auth : Authentication) : Observable<boolean> {
    return this.httpClient.post<boolean>(this.URL, auth, {withCredentials: true});
  }

  public logout() : Observable<boolean> {
    return this.httpClient.delete<boolean>(this.URL, {withCredentials: true});
  }
}

export interface Authentication {
  email: String,
  password: String
}
