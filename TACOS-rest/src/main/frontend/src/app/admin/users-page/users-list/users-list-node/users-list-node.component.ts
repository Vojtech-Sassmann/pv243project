import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from "../../../../services/user/user.service";

@Component({
  selector: 'app-users-list-node',
  templateUrl: './users-list-node.component.html',
  styleUrls: ['./users-list-node.component.css']
})
export class UsersListNodeComponent implements OnInit {

  @Input()
  public user : User;

  @Output()
  deleteAction : EventEmitter<any> = new EventEmitter();


  constructor() { }

  ngOnInit() {
  }

  public deleteUser() {
    this.deleteAction.emit(null);
  }

}
