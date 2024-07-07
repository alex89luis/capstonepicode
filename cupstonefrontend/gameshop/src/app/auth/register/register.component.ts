

import { Component } from '@angular/core';
import { Utente } from '../../models/utente';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerData: Partial<Utente> = {
    firstName: 'Alessio',
    lastName: 'Luise',
    username: 'Wesker',
    email: 'luise@gmai.com',
    citta: 'Napoli',
    codiceFiscale: 'lsulss45s02l839q',
    password: '021189'
  };
  selectedFile: File | null = null;

  constructor(
    private authSvc: AuthService,
    private router: Router
  ) { }

  signUp() {
    this.authSvc.register(this.registerData).subscribe({
      next: (data) => {
        console.log('Registrazione avvenuta con successo', data);
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        console.error('Errore durante la registrazione', err);
      }
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0]
  }
}


