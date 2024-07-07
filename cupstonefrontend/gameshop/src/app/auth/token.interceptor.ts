import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Retrieve the access data from localStorage
    const accessData = localStorage.getItem('accessData');
    console.log('Access data from localStorage:', accessData); // Debugging line

    if (accessData) {
      // Parse the access data to get the actual JWT
      const parsedData = JSON.parse(accessData);
      const token = parsedData.token;
      console.log('Parsed token:', token); // Debugging line

      // Clone the request and add the Authorization header
      const clonedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log('Cloned Request with Authorization header:', clonedRequest); // Debugging line

      return next.handle(clonedRequest);
    } else {
      // If there's no token, pass the request without modifying it
      console.warn('No token found, request is sent without Authorization header'); // Debugging line
      return next.handle(request);
    }
  }
}
