import { Component, OnInit } from '@angular/core';
import {UserCreate, UserService} from "../services/user/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-user-page',
  templateUrl: './register-user-page.component.html',
  styleUrls: ['./register-user-page.component.css']
})
export class RegisterUserPageComponent implements OnInit {

  private user : UserCreate;
  public formGroup:FormGroup;

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

  createUser(name, surname, email, password) {
    this.user = {
      name: name,
      surname: surname,
      email: email,
      password: password,
      role: "SUBMITTER"
    };
    this.userService.register(this.user).subscribe(() => {
      this.router.navigate([("/auth")]);
    });
  }
}
