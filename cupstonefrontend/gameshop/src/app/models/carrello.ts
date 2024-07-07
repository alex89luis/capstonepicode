import { Prodotto } from "./prodotto";
import { Utente } from "./utente";

export interface Carrello {
    id: number;
    prodotti: Prodotto[]; // Lista di identificatori di prodotti
    totale: number;
    utente: Utente; // Identificatore dell'utente
}
