import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import {Template} from "../template/template.service";

@Injectable()
export class ProductService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/products";

  constructor(protected httpClient:HttpClient) { }

  public createProduct(productCreate: ProductCreate) : Observable<number>  {
    return this.httpClient.post<number>(this.URL, productCreate,{withCredentials: true});
  }

  public deleteProduct(product: Product) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${product.id}`,{withCredentials: true});
  }

  public getAllProducts() : Observable<Array<Product>> {
    return this.httpClient.get<Array<Product>>(this.URL, {withCredentials: true});
  }

  public findProductById(id: number) : Observable<Product> {
    return this.httpClient.get<Product>(`${this.URL}/${id}`,{withCredentials: true});
  }

  public addTemplate(product : Product, template : Template) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${product.id}/addTemplate/${template.id}`, null, {withCredentials: true});
  }

  public removeTemplate(product : Product, template : Template) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${product.id}/removeTemplate/${template.id}`, null, {withCredentials: true});
  }
}

export interface Product {
  id: number,
  name: String,
  description: String,
  minimalPrice: number,
  templates: Template[],
  image: String
}

export interface ProductCreate {
  name: String,
  description: String,
  templateIds: number[],
  productCategoryIds: number[],
  image: any
}
