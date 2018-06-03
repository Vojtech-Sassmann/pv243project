import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductCategory, ProductCategoryService} from "../../services/product-category/product-category.service";

@Component({
  selector: 'app-product-category-detail-page',
  templateUrl: './product-category-detail-page.component.html',
  styleUrls: ['./product-category-detail-page.component.css']
})
export class ProductCategoryDetailPageComponent implements OnInit {

  productCategory: ProductCategory;
  productCategoryId: number;
  public loaded: boolean = false;

  constructor(private productCategoryService: ProductCategoryService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.params.subscribe(res => {
      this.productCategoryId = res.id;
      this.loadProductCategory();
    });
  }

  loadProductCategory() {
    this.productCategoryService.findProductCategoryById(this.productCategoryId).subscribe((productCategory) => {
      this.productCategory = productCategory;
      this.loaded = true;
    });
  }
}
