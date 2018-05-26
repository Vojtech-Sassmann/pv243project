import {Component, OnInit, ViewChild} from '@angular/core';
import {Order, OrderService} from "../../services/order/order.service";
import {UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";
import {ModalDirective} from "angular-bootstrap-md";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {

  @ViewChild('autoShownModal') public autoShownModal:ModalDirective;
  public isModalShown:boolean = true;

  order : Order;
  public loaded: boolean = false;

  constructor(private orderService: OrderService, private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.loadBasket();
  }

  loadBasket() {
    this.userService.getBasket().subscribe((order) => {
      this.order = order;
      this.loaded = true;
      console.log("BasketComponent" + this.order);
    });
  }

  submitOrder() {
    this.orderService.submitOrder(this.order).subscribe(() => {
      this.showModal();
    });
  }

  public showModal():void {
    this.isModalShown = true;
  }

  public hideModal():void {
    this.autoShownModal.hide();
  }

  public onHidden():void {
    this.isModalShown = false;
  }
}
