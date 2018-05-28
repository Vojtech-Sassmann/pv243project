import {Injectable} from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import {Router} from "@angular/router";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router : Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .catch((err: HttpErrorResponse) => {

        console.log(err.status);

        if (err.status == 401) {
          this.router.navigate([("/auth")]);
          return Observable.throw(err);
        }
        if (err.status == 403) {
          this.router.navigate([("/notAllowed")])
        }
        let errors = [];
        if (typeof err.error === "string") {
          errors.push(JSON.parse(err.error).errors);
        } else{
          errors.push(err.error.errors);
        }

        if (errors == undefined || errors.length == 0) {
          errors.push(err.statusText);
        }

        if (err.status == 0) {
          errors.push("Server is not accessible.");
        }

        return Observable.throw(err);
      });
  }
}
