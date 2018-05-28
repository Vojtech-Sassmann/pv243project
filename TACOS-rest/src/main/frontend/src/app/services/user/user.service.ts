import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import {Order} from "../order/order.service";

@Injectable()
export class UserService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/users";

  constructor(protected httpClient:HttpClient) { }

  public createUser(userCreate: UserCreate) : Observable<User>  {
    return this.httpClient.post<User>(this.URL, userCreate,{withCredentials: true});
  }

  public register(userCreate: UserCreate) : Observable<User>  {
    return this.httpClient.post<User>(this.URL + "/register", userCreate,{withCredentials: true});
  }

  public deleteUser(user: User) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${user.id}`,{withCredentials: true});
  }

  public loadUsers(): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(this.URL, {withCredentials: true});
  }

  public findUserById(id: Number) : Observable<User> {
    return this.httpClient.get<User>(`${this.URL}/${id}`,{withCredentials: true});
  }

  public findUserByEmail(email: String) : Observable<User> {
    return this.httpClient.get<User>(`${this.URL}/filter/email/${email}`,{withCredentials: true});
  }

  public getAllUsersForRole(userRole: String): Observable<Array<User>> {
    return this.httpClient.get<Array<User>>(`${this.URL}/filter/role/${userRole}`, {withCredentials: true});
  }

  public addOrderToUser(user : User, order : Order) : Observable<number>{
    return this.httpClient.put<number>(`${this.URL}/${user.id}/addOrder/${order.id}`, null, {withCredentials: true});
  }

  public removeOrderFromUser(user : User, order : Order) : Observable<number>{
    return this.httpClient.put<number>(`${this.URL}/${user.id}/removeOrder/${order.id}`, null, {withCredentials: true});
  }

  public setSubmitter(user: User)  {
    return this.httpClient.put( `${this.URL}/setSubmitter?id=${user.id}`, null, {withCredentials: true});
  }

  public setPractitioner(user: User) {
    return this.httpClient.put( `${this.URL}/setPractitioner?id=${user.id}`,null, {withCredentials: true});
  }

  public setSuperAdmin(user: User) {
    return this.httpClient.put( `${this.URL}/setSuperadmin?id=${user.id}`,null, {withCredentials: true});
  }

  public getBasket() : Observable<Order> {
    console.log("Test");
    return this.httpClient.get<Order>(`${this.URL}/getBasket`,{withCredentials: true});
  }

}

export interface User {
  id: Number,
  name: String,
  surname: String,
  email: String,
  role: String,
  orders: Order[]
}

export interface UserCreate {
  name: String,
  surname: String,
  email: String,
  role: String,
  password: String
}

export interface UserAuthenticate {
  email: String,
  password: String
}
