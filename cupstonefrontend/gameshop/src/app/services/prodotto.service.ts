import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { Prodotto } from '../models/prodotto';

@Injectable({
  providedIn: 'root'
})
export class ProdottoService {
  private baseUrl = 'http://localhost:8080/api/prodotti';  // Change with your backend URL if different

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private prodottiSubject: BehaviorSubject<Prodotto[]> = new BehaviorSubject<Prodotto[]>([]);
  prodotti$: Observable<Prodotto[]> = this.prodottiSubject.asObservable();

  constructor(private http: HttpClient) { }

  getAllProdotti(): Observable<Prodotto[]> {
    return this.http.get<Prodotto[]>(this.baseUrl).pipe(
      map(prodotti => {
        this.prodottiSubject.next(prodotti);
        return prodotti;
      })
    );
  }

  getProdotto(id: number): Observable<Prodotto> {
    return this.http.get<Prodotto>(`${this.baseUrl}/${id}`);
  }

  createProdotto(prodotto: Prodotto): Observable<Prodotto> {
    return this.http.post<Prodotto>(this.baseUrl, prodotto, this.httpOptions).pipe(
      map(newProdotto => {
        this.addProdottoToSubject(newProdotto);
        return newProdotto;
      })
    );
  }

  updateProdotto(id: number, prodotto: Prodotto): Observable<Prodotto> {
    return this.http.put<Prodotto>(`${this.baseUrl}/${id}`, prodotto, this.httpOptions).pipe(
      map(updatedProdotto => {
        this.updateProdottoInSubject(updatedProdotto);
        return updatedProdotto;
      })
    );
  }

  deleteProdotto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.httpOptions).pipe(
      map(() => {
        this.removeProdottoFromSubject(id);
      })
    );
  }

  // BehaviorSubject Methods
  private addProdottoToSubject(prodotto: Prodotto) {
    const prodotti = this.prodottiSubject.value;
    this.prodottiSubject.next([...prodotti, prodotto]);
  }

  private updateProdottoInSubject(prodotto: Prodotto) {
    const prodotti = this.prodottiSubject.value.map(p => p.id === prodotto.id ? prodotto : p);
    this.prodottiSubject.next(prodotti);
  }

  private removeProdottoFromSubject(id: number) {
    const prodotti = this.prodottiSubject.value.filter(p => p.id !== id);
    this.prodottiSubject.next(prodotti);
  }

  clearProdottiSubject() {
    this.prodottiSubject.next([]);
  }
}