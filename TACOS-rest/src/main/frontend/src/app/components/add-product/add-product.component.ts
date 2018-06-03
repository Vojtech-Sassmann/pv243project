import { Component, OnInit } from '@angular/core';
import { ProductCreate, ProductService } from "../../services/product/product.service";
import { ProductCategory, ProductCategoryService } from "../../services/product-category/product-category.service";
import { Template, TemplateService } from "../../services/template/template.service";
import  {FormBuilder, FormGroup, Validators } from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  public categories : ProductCategory[] = [];
  public templates : Template[] = [];
  public formGroup : FormGroup;
  public data : any;

  public productToBeCreated : ProductCreate = {
    "name" : "",
    "description" : "",
    "productCategoryIds" : [],
    "templateIds" : [],
    "image" : null
  };

  constructor(private productService : ProductService,
              private productCategoryService : ProductCategoryService,
              private templateService : TemplateService,
              private router: Router,
              private fb : FormBuilder) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      nameCtrl: ['', Validators.required],
    });
    this.productCategoryService.getLeafProductCategories().subscribe(categories => this.categories = categories);
    this.templateService.getAllTemplates().subscribe(templates => this.templates = templates);
  }

  public submit() : void {
    this.productService.createProduct(this.productToBeCreated).subscribe((newProductId) =>
      this.router.navigate([('/marketplace/eshop')])
    )
  }

  public fileSelected(event) {
    let reader = new FileReader();
    if (event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.productToBeCreated.image = reader.result.split(',')[1];
      };
    }
  }
}
