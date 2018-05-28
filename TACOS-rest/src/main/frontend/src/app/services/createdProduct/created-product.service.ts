import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import {Attribute} from "../attribute/attribute.service";
import {Product} from "../product/product.service";

@Injectable()
export class CreatedProductService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/createdProducts";

  constructor(protected httpClient:HttpClient) { }

  public createCreatedProduct(createdProductCreate: CreatedProductCreate) : Observable<number>  {
    return this.httpClient.post<number>(this.URL, createdProductCreate,{withCredentials: true});
  }

  public deleteCreatedProduct(createdProduct: CreatedProduct) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${createdProduct.id}`,{withCredentials: true});
  }

  public getAllCreatedProducts() : Observable<Array<CreatedProduct>> {
    return this.httpClient.get<Array<CreatedProduct>>(this.URL, {withCredentials: true});
  }

  public findCreatedProductById(id: number) : Observable<CreatedProduct> {
    return this.httpClient.get<CreatedProduct>(`${this.URL}/${id}`,{withCredentials: true});
  }

  public addAttribute(createdProduct : CreatedProduct, attribute : Attribute) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${createdProduct.id}/addAttribute/${attribute.id}`, null, {withCredentials: true});
  }

  public removeAttribute(createdProduct : CreatedProduct, attribute : Attribute) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${createdProduct.id}/removeAttribute/${attribute.id}`, null, {withCredentials: true});
  }
}

export interface CreatedProduct {
  id: number,
  price: number,
  count: number,
  description: String,
  product: Product,
  attributes: Attribute[]
}

export interface CreatedProductCreate {
  count: number,
  description: String,
  productId: number,
  attributeIds: number[]
}
