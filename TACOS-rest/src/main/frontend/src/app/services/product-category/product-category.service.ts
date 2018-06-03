import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {Product} from "../product/product.service";

@Injectable()
export class ProductCategoryService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/productCategories";

  constructor(protected httpClient : HttpClient) { }

  public createProductCategory(productCategoryCreate: ProductCategoryCreate) : Observable<number>  {
    return this.httpClient.post<number>(this.URL, productCategoryCreate,{withCredentials: true});
  }

  public deleteProductCategory(productCategory: ProductCategory) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${productCategory.id}`,{withCredentials: true});
  }

  public getAllProductCategories() : Observable<Array<ProductCategory>> {
    return this.httpClient.get<Array<ProductCategory>>(this.URL, {withCredentials: true});
  }

  public getLeafProductCategories() : Observable<Array<ProductCategory>> {
    return this.httpClient.get<Array<ProductCategory>>(`${this.URL}/filter/leaf`, {});
  }

  public findProductCategoryById(id: number) : Observable<ProductCategory> {
    return this.httpClient.get<ProductCategory>(`${this.URL}/${id}`,{withCredentials: true});
  }

  public addSubCategory(productCategory : ProductCategory, subCategory : ProductCategory) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${productCategory.id}/addSubCategory/${subCategory.id}`, null, {withCredentials: true});
  }

  public removeSubCategory(productCategory : ProductCategory, subCategory : ProductCategory) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${productCategory.id}/removeSubCategory/${subCategory.id}`, null, {withCredentials: true});
  }

  public addProduct(productCategory : ProductCategory, product : Product) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${productCategory.id}/addProduct/${product.id}`, null, {withCredentials: true});
  }

  public removeProduct(productCategory : ProductCategory, product : Product) : Observable<number> {
    return this.httpClient.put<number>(`${this.URL}/${productCategory.id}/removeProduct/${product.id}`, null, {withCredentials: true});
  }
}

export interface ProductCategory {
  id: number,
  name: String,
  image: String,
  products: Product[],
  parentCategoryId: number,
  subCategories: ProductCategory[]
}

export interface ProductCategoryCreate {
  name: String,
  image: String,
  parentCategoryId: number
}
