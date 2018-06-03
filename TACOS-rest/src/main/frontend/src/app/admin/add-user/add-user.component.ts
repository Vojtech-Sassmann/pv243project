import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserCreate, UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent implements OnInit {

  private user : UserCreate;
  public formGroup:FormGroup;
  public userRole : string;

  constructor(private userService : UserService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      nameCtrl: ['', Validators.required],
      surnameCtrl: ['', Validators.required],
      emailCtrl: ['', Validators.required],
      passwdCtrl: ['', Validators.required],
      passwd2Ctrl: ['', Validators.required],
    });

  }

  createUser(name, surname, role, email, password) {
    this.user = {
      name: name,
      surname: surname,
      email: email,
      password: password,
      role: role,
    };
    this.userService.createUser(this.user).subscribe(() => {
      this.router.navigate([("/admin/users")]);
    });
  }

}
