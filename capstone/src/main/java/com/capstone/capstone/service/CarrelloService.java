package com.capstone.capstone.service;

import com.capstone.capstone.dto.CarrelloDto;
import com.capstone.capstone.entity.Carrello;
import com.capstone.capstone.entity.Prodotto;
import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.CarrelloNotFoundException;
import com.capstone.capstone.exception.ProdottoNotFoundException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.repository.CarrelloRepository;
import com.capstone.capstone.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProdottoService prodottoService;

    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Carrello getCarrello(Long id) throws CarrelloNotFoundException {
        return carrelloRepository.findById(id)
                .orElseThrow(() -> new CarrelloNotFoundException("Carrello not found"));
    }

    public Carrello createCarrello(CarrelloDto carrelloDto) throws UtenteNotFoundException, ProdottoNotFoundException {
        Carrello carrello = new Carrello();

        // Carica l'utente dal repository degli utenti utilizzando l'ID dell'utente
        Utente utente = utenteRepository.findById(carrelloDto.getUtenteId())
                .orElseThrow(() -> new UtenteNotFoundException("Utente not found"));
        carrello.setUtente(utente);

        // Carica i prodotti dal repository e aggiungili al carrello
        List<Prodotto> prodotti = new ArrayList<>();
        for (Long prodottoId : carrelloDto.getProdotti()) {
            Prodotto prodotto = prodottoService.getProdotto(prodottoId);
            prodotti.add(prodotto);
        }
        carrello.setProdotti(prodotti);

        // Imposta il totale
        carrello.setTotale(carrelloDto.getTotale());

        return carrelloRepository.save(carrello);
    }

    public Carrello updateCarrello(Long id, CarrelloDto carrelloDto) throws CarrelloNotFoundException, ProdottoNotFoundException {
        Carrello carrello = carrelloRepository.findById(id)
                .orElseThrow(() -> new CarrelloNotFoundException("Carrello not found"));
        List<Prodotto> prodotti = new ArrayList<>();
        for (Long prodottoId : carrelloDto.getProdotti()) {
            Prodotto prodotto = prodottoService.getProdotto(prodottoId);
            prodotti.add(prodotto);
        }
        carrello.setProdotti(prodotti);
        carrello.setTotale(carrelloDto.getTotale());
        return carrelloRepository.save(carrello);
    }

    public void deleteCarrello(Long id) throws CarrelloNotFoundException {
        Carrello carrello = carrelloRepository.findById(id)
                .orElseThrow(() -> new CarrelloNotFoundException("Carrello not found"));
        carrelloRepository.delete(carrello);
    }
}
