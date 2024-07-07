import { Component, OnInit } from '@angular/core';
import { Prodotto } from '../../models/prodotto';
import { ProdottoService } from '../../services/prodotto.service';
import { CarrelloService } from '../../services/carrello.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  prodotti: Prodotto[] = [];

  constructor(
    private prodottoService: ProdottoService,
    private carrelloService: CarrelloService
  ) { }

  ngOnInit(): void {
    this.prodottoService.prodotti$.subscribe(data => {
      this.prodotti = data;
    });
    this.getAllProdotti();
  }

  getAllProdotti(): void {
    this.prodottoService.getAllProdotti().subscribe();
  }

  addProdottoToCarrello(prodotto: Prodotto): void {
    this.carrelloService.addProdotto(prodotto);
  }
}