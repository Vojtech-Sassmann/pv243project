import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserDetailIdComponent} from "./components/user-detail-id/user-detail-id.component";
import { MarketplaceComponent } from './marketplace/marketplace.component';
import { EshopComponent } from './marketplace/eshop/eshop.component';
import { BasketComponent } from './marketplace/basket/basket.component';
import { SubcategoryComponent } from './marketplace/eshop/subcategory/subcategory.component';
import { ProductDetailComponent } from './marketplace/eshop/product-detail/product-detail.component';
import {AdminComponent} from "./admin/admin.component";
import {AdminProfileComponent} from "./admin/admin-profile/admin-profile.component";
import {UsersPageComponent} from "./admin/users-page/users-page.component";
import {OrdersPageComponent} from "./admin/orders-page/orders-page.component";
import {RegisterUserPageComponent} from "./register-user-page/register-user-page.component";
import {LoginPageComponent} from "./login-page/login-page.component";
import {AddUserComponent} from "./admin/add-user/add-user.component";
import {MarketplaceOrderDetailComponent} from "./marketplace/marketplace-order-detail/marketplace-order-detail.component";
import {MarketplaceProfileComponent} from "./marketplace/marketplace-profile/marketplace-profile.component";
import {AdminOrderDetailComponent} from "./admin/admin-order-detail/admin-order-detail.component";
import {AddProductComponent} from "./components/add-product/add-product.component";
import {AddProductPageComponent} from "./admin/add-product-page/add-product-page.component";
import {TemplateDetailPageComponent} from "./components/template-detail-page/template-detail-page.component";
import {AttributeCategoryDetailPageComponent} from "./components/attribute-category-detail-page/attribute-category-detail-page.component";
import {AttributeDetailPageComponent} from "./components/attribute-detail-page/attribute-detail-page.component";
import {ProductCategoryDetailPageComponent} from "./components/product-category-detail-page/product-category-detail-page.component";
import {AttributesPageComponent} from "./admin/attributes-page/attributes-page.component";
import {AttributeCategoriesPageComponent} from "./admin/attribute-categories-page/attribute-categories-page.component";
import {TemplatesPageComponent} from "./admin/templates-page/templates-page.component";
import {ProductCategoriesPageComponent} from "./admin/product-categories-page/product-categories-page.component";
import {CreateTemplateComponent} from "./admin/create-template/create-template.component";
import {CreateProductCategoryComponent} from "./admin/create-product-category/create-product-category.component";
import {CreateAttributeComponent} from "./admin/create-attribute/create-attribute.component";
import {CreateAttributeCategoryComponent} from "./admin/create-attribute-category/create-attribute-category.component";
import {ProductsPageComponent} from "./admin/products-page/products-page.component";
import {ProductDetailPageComponent} from "./components/product-detail-page/product-detail-page.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {NotAllowedComponent} from "./components/not-allowed/not-allowed.component";



const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      {
        path: '',
        redirectTo: '/admin/profile',
        pathMatch: 'full'
      },
      {
        path: 'profile',
        component: AdminProfileComponent
      },
      {
        path: 'users',
        component: UsersPageComponent
      },
      {
        path: 'addUser',
        component: AddUserComponent
      },
      {
        path: 'create-template',
        component: CreateTemplateComponent
      },
      {
        path: 'create-attribute-category',
        component: CreateAttributeCategoryComponent
      },
      {
        path: 'create-attribute',
        component: CreateAttributeComponent
      },
      {
        path: 'create-product-category',
        component: CreateProductCategoryComponent
      },
      {
        path: 'orders',
        component: OrdersPageComponent
      },
      {
        path: 'attributes',
        component: AttributesPageComponent
      },
      {
        path: 'attribute-categories',
        component: AttributeCategoriesPageComponent
      },
      {
        path: 'products',
        component: ProductsPageComponent
      },
      {
        path: 'product-categories',
        component: ProductCategoriesPageComponent
      },
      {
        path: 'templates',
        component: TemplatesPageComponent
      },
      {
        path: 'orders/:id',
        component: AdminOrderDetailComponent
      },
      {
        path: 'users/:id',
        component: UserDetailIdComponent
      },
      {
        path:'addProduct',
        component: AddProductPageComponent
      },
      {
        path: 'templates/:id',
        component: TemplateDetailPageComponent
      },
      {
        path: 'products/:id',
        component: ProductDetailPageComponent
      },
      {
        path: 'attribute-categories/:id',
        component: AttributeCategoryDetailPageComponent
      },
      {
        path: 'attributes/:id',
        component: AttributeDetailPageComponent
      },
      {
        path: 'product-categories/:id',
        component: ProductCategoryDetailPageComponent
      },
    ]
  },
  {
    path: 'marketplace',
    component: MarketplaceComponent,
    children: [
      {
        path: 'eshop',
        component: EshopComponent
      },
      {
        path: 'profile',
        component: MarketplaceProfileComponent
      },
      {
        path: 'orders/:id',
        component: MarketplaceOrderDetailComponent
      },

      {
        path: 'basket',
        component: BasketComponent
      },
      {
        path: 'eshop-category/:id',
        component: SubcategoryComponent
      },
      {
        path: 'product/:id',
        component: ProductDetailComponent
      },
      {
        path: '',
        redirectTo: '/marketplace/eshop',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: 'auth',
    component: LoginPageComponent
  },
  {
    path: 'register',
    component: RegisterUserPageComponent,
  },
  {
    path: 'notfound',
    component: PageNotFoundComponent
  },
  {
    path: 'notAllowed',
    component: NotAllowedComponent
  },
  {
    path: '**',
    redirectTo: '/marketplace/eshop'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
