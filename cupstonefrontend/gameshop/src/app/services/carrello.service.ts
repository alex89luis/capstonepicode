import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Carrello } from '../models/carrello';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { CarrelloDto } from '../models/carrello-dto';
import { Prodotto } from '../models/prodotto';

@Injectable({
  providedIn: 'root'
})
export class CarrelloService {

  private baseUrl = 'http://localhost:8080/api/carrelli';  // Cambia con l'URL del tuo backend se diverso

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private prodottiSubject: BehaviorSubject<Prodotto[]> = new BehaviorSubject<Prodotto[]>([]);
  prodotti$: Observable<Prodotto[]> = this.prodottiSubject.asObservable();

  constructor(private http: HttpClient) {}

  addProdotto(prodotto: Prodotto): void {
    const prodotti = this.prodottiSubject.value;
    this.prodottiSubject.next([...prodotti, prodotto]);
  }

  checkout(): Observable<CarrelloDto> {
    const prodotti = this.prodottiSubject.value;
    const prodottoIds = prodotti.map(prodotto => prodotto.id);
    const carrelloDto: CarrelloDto = {
      id: 0, // Assicurati di gestire l'ID correttamente lato server
      prodotti: prodottoIds,
      totale: prodotti.reduce((total, prod) => total + prod.prezzo, 0),
      utente: 1 // Assicurati di impostare l'utente corretto
    };
    return this.http.post<CarrelloDto>(this.baseUrl, carrelloDto, this.httpOptions);
  }

  clearCarrello(): void {
    this.prodottiSubject.next([]);
  }
}
