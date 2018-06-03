import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import {CreatedProduct} from "../createdProduct/created-product.service";

@Injectable()
export class OrderService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/orders";

  constructor(protected httpClient:HttpClient) { }

  public deleteOrder(order: Order) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${order.id}`,{withCredentials: true});
  }

  public getAllOrders() : Observable<Array<Order>> {
    return this.httpClient.get<Array<Order>>(this.URL, {withCredentials: true});
  }

  public findOrderById(id: Number) : Observable<Order> {
    return this.httpClient.get<Order>(`${this.URL}/${id}`,{withCredentials: true});
  }

  public removeCreatedProduct(order : Order, createdProduct : CreatedProduct, ){
    console.log(`${this.URL}/${order.id}/removeCreatedProduct?createdProductId=${createdProduct.id}`);
    return this.httpClient.put(`${this.URL}/${order.id}/removeCreatedProduct?createdProductId=${createdProduct.id}`, null, {withCredentials: true});
  }

  public getAllOrdersForState(orderState : String) : Observable<Array<Order>> {
    return this.httpClient.get<Array<Order>>(`${this.URL}/filter/state/${orderState}`, {withCredentials: true});
  }

  public getAllOrdersWithoutNewOrders() : Observable<Array<Order>> {
    return this.httpClient.get<Array<Order>>(`${this.URL}/filter/notNew`, {withCredentials: true});
  }

  public submitOrder(order: Order) : Observable<number>  {
    return this.httpClient.put<number>( `${this.URL}/submitOrder?id=${order.id}`, null, {withCredentials: true});
  }

  public cancelOrder(order: Order) : Observable<number>  {
    return this.httpClient.put<number>( `${this.URL}/cancelOrder?id=${order.id}`,null, {withCredentials: true});
  }

  public finishOrder(order: Order) : Observable<number>  {
    return this.httpClient.put<number>( `${this.URL}/finishOrder?id=${order.id}`,null, {withCredentials: true});
  }

  public processOrder(order: Order) : Observable<number>  {
    return this.httpClient.put<number>( `${this.URL}/processOrder?id=${order.id}`,null, {withCredentials: true});
  }
}

export interface localDate {
  dayOfMonth: number,
  month: number,
  year: number
}


export interface Order {
  id: number,
  state: String,
  submitted: localDate,
  submitterId: number,
  finished: localDate,
  price: number,
  products: CreatedProduct[]
}
