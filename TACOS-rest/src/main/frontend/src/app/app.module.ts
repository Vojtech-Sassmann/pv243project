import { BrowserModule } from '@angular/platform-browser';
import { NgModule , NO_ERRORS_SCHEMA} from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';



import { AppComponent } from './app.component';


import { FormsModule } from '@angular/forms';
import { UserDetailComponent } from './components/user-detail-id/user-detail/user-detail.component';
import { UserDetailIdComponent } from './components/user-detail-id/user-detail-id.component';
import { MarketplaceComponent } from './marketplace/marketplace.component';
import { MarketplaceHeaderComponent } from './marketplace/marketplace-header/marketplace-header.component';
import { EshopComponent } from './marketplace/eshop/eshop.component';
import { BasketComponent } from './marketplace/basket/basket.component';

import { UserService } from "./services/user/user.service";
import { ProductCategoryService } from './services/product-category/product-category.service';
import { EshopCategoriesComponent } from './marketplace/eshop/eshop-categories/eshop-categories.component';
import { EshopCategoryComponent } from './marketplace/eshop/eshop-categories/eshop-category/eshop-category.component';

import {ProductService} from "./services/product/product.service";
import {OrderService} from "./services/order/order.service";
import {CreatedProductService} from "./services/createdProduct/created-product.service";
import {TemplateService} from "./services/template/template.service";
import {AttributeService} from "./services/attribute/attribute.service";
import {AttributeCategoryService} from "./services/attributeCategory/attribute-category.service";
import { SubcategoryComponent } from './marketplace/eshop/subcategory/subcategory.component';
import { ProductIconComponent } from './marketplace/eshop/subcategory/product-icon/product-icon.component';
import { ProductDetailComponent } from './marketplace/eshop/product-detail/product-detail.component';
import { TemplateSelectorComponent } from './marketplace/eshop/product-detail/template-selector/template-selector.component';
import { AttributeCategoriesComponent } from './marketplace/eshop/product-detail/attribute-categories/attribute-categories.component';
import { AttributeSelectorComponent } from './marketplace/eshop/product-detail/attribute-categories/attribute-selector/attribute-selector.component';
import { AdminHeaderComponent } from './admin/admin-header/admin-header.component';
import { AdminComponent } from './admin/admin.component';
import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
import { UsersListComponent } from './admin/users-page/users-list/users-list.component';
import { UsersPageComponent } from './admin/users-page/users-page.component';
import { UsersListNodeComponent } from './admin/users-page/users-list/users-list-node/users-list-node.component';
import { OrderListComponent } from './components/order-list/order-list.component';
import { OrderListNodeComponent } from './components/order-list/order-list-node/order-list-node.component';
import { OrdersPageComponent } from './admin/orders-page/orders-page.component';
import { RegisterUserPageComponent } from './register-user-page/register-user-page.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { AddUserComponent } from './admin/add-user/add-user.component';
import { OrderDetailComponent } from './components/order-detail/order-detail.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductListNodeComponent } from './components/product-list/product-list-node/product-list-node.component';
import { BasketProductListComponent } from './marketplace/basket/basket-product-list/basket-product-list.component';
import { BasketProductListNodeComponent } from './marketplace/basket/basket-product-list/basket-product-list-node/basket-product-list-node.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AddProductPageComponent } from './admin/add-product-page/add-product-page.component';
import { FooterComponent } from './components/footer/footer.component';
import { MarketplaceProfileComponent } from './marketplace/marketplace-profile/marketplace-profile.component';
import {ProfileComponent} from "./components/profile/profile.component";
import { AdminOrderDetailComponent } from './admin/admin-order-detail/admin-order-detail.component';
import { TemplateListComponent } from './components/template-list/template-list.component';
import { TemplateListNodeComponent } from './components/template-list/template-list-node/template-list-node.component';
import { TemplateDetailPageComponent } from './components/template-detail-page/template-detail-page.component';
import { TemplateDetailComponent } from './components/template-detail-page/template-detail/template-detail.component';
import { AttributeCategoryListComponent } from './components/attribute-category-list/attribute-category-list.component';
import { AttributeCategoryListNodeComponent } from './components/attribute-category-list/attribute-category-list-node/attribute-category-list-node.component';
import { AttributeCategoryDetailPageComponent } from './components/attribute-category-detail-page/attribute-category-detail-page.component';
import { AttributeCategoryDetailComponent } from './components/attribute-category-detail-page/attribute-category-detail/attribute-category-detail.component';
import { AttributeListComponent } from './components/attribute-list/attribute-list.component';
import { AttributeListNodeComponent } from './components/attribute-list/attribute-list-node/attribute-list-node.component';
import { AttributeDetailPageComponent } from './components/attribute-detail-page/attribute-detail-page.component';
import { AttributeDetailComponent } from './components/attribute-detail-page/attribute-detail/attribute-detail.component';
import { ProductCategoryListComponent } from './components/product-category-list/product-category-list.component';
import { ProductCategoryListNodeComponent } from './components/product-category-list/product-category-list-node/product-category-list-node.component';
import { ProductCategoryDetailPageComponent } from './components/product-category-detail-page/product-category-detail-page.component';
import { ProductCategoryDetailComponent } from './components/product-category-detail-page/product-category-detail/product-category-detail.component';
import { TemplatesPageComponent } from './admin/templates-page/templates-page.component';
import { AttributeCategoriesPageComponent } from './admin/attribute-categories-page/attribute-categories-page.component';
import { AttributesPageComponent } from './admin/attributes-page/attributes-page.component';
import { ProductCategoriesPageComponent } from './admin/product-categories-page/product-categories-page.component';
import { CreateTemplateComponent } from './admin/create-template/create-template.component';
import { CreateAttributeComponent } from './admin/create-attribute/create-attribute.component';
import { CreateAttributeCategoryComponent } from './admin/create-attribute-category/create-attribute-category.component';
import { CreateProductCategoryComponent } from './admin/create-product-category/create-product-category.component';
import { ProductsSelectorComponent } from './admin/create-template/products-selector/products-selector.component';
import { AttributeCategoriesSelectorComponent } from './admin/create-template/attribute-categories-selector/attribute-categories-selector.component';
import { AttributesSelectorComponent } from './admin/create-attribute-category/attributes-selector/attributes-selector.component';
import { TemplatesSelectorComponent } from './admin/create-attribute-category/templates-selector/templates-selector.component';
import { AttributeCategorySelectorComponent } from './admin/create-attribute/attribute-category-selector/attribute-category-selector.component';
import { ProductCategorySelectorComponent } from './admin/create-product-category/product-category-selector/product-category-selector.component';
import { CreatedProductListComponent } from './components/created-product-list/created-product-list.component';
import { CreatedProductListNodeComponent } from './components/created-product-list/created-product-list-node/created-product-list-node.component';
import { ProductsPageComponent } from './admin/products-page/products-page.component';
import { ProductDetailPageComponent } from './components/product-detail-page/product-detail-page.component';
import { ProductDetailAdminComponent } from './components/product-detail-page/product-detail-admin/product-detail-admin.component';
import {MarketplaceOrderDetailComponent} from "./marketplace/marketplace-order-detail/marketplace-order-detail.component";
import {AuthService} from "./services/auth/auth.service";
import {ErrorInterceptor} from "./error-interceptor";
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { NotAllowedComponent } from './components/not-allowed/not-allowed.component';

@NgModule({
  declarations: [
    AppComponent,
    UserDetailComponent,
    UserDetailIdComponent,
    MarketplaceComponent,
    MarketplaceHeaderComponent,
    EshopComponent,
    ProfileComponent,
    BasketComponent,
    EshopCategoriesComponent,
    EshopCategoryComponent,
    SubcategoryComponent,
    ProductIconComponent,
    ProductDetailComponent,
    TemplateSelectorComponent,
    AttributeCategoriesComponent,
    AttributeSelectorComponent,
    AdminHeaderComponent,
    AdminComponent,
    AdminProfileComponent,
    UsersListComponent,
    UsersPageComponent,
    UsersListNodeComponent,
    OrderListComponent,
    OrderListNodeComponent,
    OrdersPageComponent,
    RegisterUserPageComponent,
    HeaderComponent,
    LoginPageComponent,
    AddUserComponent,
    MarketplaceOrderDetailComponent,
    OrderDetailComponent,
    ProductListComponent,
    ProductListNodeComponent,
    BasketProductListComponent,
    BasketProductListNodeComponent,
    AddProductComponent,
    AddProductPageComponent,
    FooterComponent,
    MarketplaceProfileComponent,
    AdminOrderDetailComponent,
    AddProductPageComponent,
    TemplateListComponent,
    TemplateListNodeComponent,
    TemplateDetailPageComponent,
    TemplateDetailComponent,
    AttributeCategoryListComponent,
    AttributeCategoryListNodeComponent,
    AttributeCategoryDetailPageComponent,
    AttributeCategoryDetailComponent,
    AttributeListComponent,
    AttributeListNodeComponent,
    AttributeDetailPageComponent,
    AttributeDetailComponent,
    ProductCategoryListComponent,
    ProductCategoryListNodeComponent,
    ProductCategoryDetailPageComponent,
    ProductCategoryDetailComponent,
    TemplatesPageComponent,
    AttributeCategoriesPageComponent,
    AttributesPageComponent,
    ProductCategoriesPageComponent,
    CreateTemplateComponent,
    CreateAttributeComponent,
    CreateAttributeCategoryComponent,
    CreateProductCategoryComponent,
    ProductsSelectorComponent,
    AttributeCategoriesSelectorComponent,
    AttributesSelectorComponent,
    TemplatesSelectorComponent,
    AttributeCategorySelectorComponent,
    ProductCategorySelectorComponent,
    CreatedProductListComponent,
    CreatedProductListNodeComponent,
    ProductsPageComponent,
    ProductDetailPageComponent,
    ProductDetailAdminComponent,
    PageNotFoundComponent,
    NotAllowedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MDBBootstrapModule.forRoot(),
    ReactiveFormsModule,
    NgSelectModule
  ],
  providers: [
    UserService,
    ProductCategoryService,
    ProductService,
    OrderService,
    CreatedProductService,
    TemplateService,
    AttributeService,
    AttributeCategoryService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent],
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class AppModule { }
