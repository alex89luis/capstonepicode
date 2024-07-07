import { Component, OnInit } from '@angular/core';
import { Carrello } from '../../models/carrello';
import { CarrelloService } from '../../services/carrello.service';
import { Prodotto } from '../../models/prodotto';
import { CarrelloDto } from '../../models/carrello-dto';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent implements OnInit {
  prodotti: Prodotto[] = [];
  checkoutSuccess: boolean = false;

  constructor(private carrelloService: CarrelloService) { 
    this.carrelloService.prodotti$.subscribe(data => {
      this.prodotti = data;
    });
  }

  ngOnInit(): void {
    // Nessuna azione necessaria al momento
  }

  confirmCheckout(): void {
    this.checkoutSuccess = true;
    this.carrelloService.clearCarrello();
    setTimeout(() => {
      const successModalElement = document.getElementById('successModal');
      if (successModalElement) {
        const successModal = new Modal(successModalElement);
        successModal.show();
      }
    }, 500); // Mostra la modale di successo dopo 500ms
  }

  clearCarrello(): void {
    this.carrelloService.clearCarrello();
  }
}