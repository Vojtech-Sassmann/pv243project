import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-marketplace-header',
  templateUrl: './marketplace-header.component.html',
  styleUrls: ['./marketplace-header.component.css']
})
export class MarketplaceHeaderComponent implements OnInit {


  constructor(private authService : AuthService, private router : Router) { }

  ngOnInit() {
  }

  public logout() : void {
    this.authService.logout().subscribe(() => this.router.navigate([("/auth")]));
  }
}
