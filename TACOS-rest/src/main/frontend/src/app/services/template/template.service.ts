import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {AttributeCategory} from "../attributeCategory/attribute-category.service";

@Injectable()
export class TemplateService {

  private readonly URL = "http://localhost:8080/TACOS-rest/api/v1/auth/templates";

  constructor(protected httpClient : HttpClient) { }

  public createTemplate(templateCreate: TemplateCreate) : Observable<number>  {
    return this.httpClient.post<number>(this.URL, templateCreate,{withCredentials: true});
  }

  public deleteTemplate(template: Template) : Observable<number>  {
    return this.httpClient.delete<number>( `${this.URL}/${template.id}`,{withCredentials: true});
  }

  public getAllTemplates(): Observable<Array<Template>> {
    return this.httpClient.get<Array<Template>>(this.URL, {withCredentials: true});
  }

  public findTemplateById(id: number) : Observable<Template> {
    return this.httpClient.get<Template>(`${this.URL}/${id}`,{withCredentials: true});
  }

}


export interface Template {
  id: number,
  name: String,
  minimalPrice: number,
  attributeCategories: AttributeCategory[]
}

export interface TemplateCreate {
  name: String,
  attributeCategoryIds: number[],
  productIds: number[]

}
