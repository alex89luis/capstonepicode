package com.capstone.capstone.service;

import com.capstone.capstone.dto.CarrelloDto;
import com.capstone.capstone.dto.UtenteDto;
import com.capstone.capstone.entity.Carrello;
import com.capstone.capstone.entity.Prodotto;
import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.CarrelloNotFoundException;
import com.capstone.capstone.exception.ProdottoNotFoundException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.repository.CarrelloRepository;
import com.capstone.capstone.repository.ProdottoRepository;
import com.capstone.capstone.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CarrelloService carrelloService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente getUserByEmail(String email) throws UtenteNotFoundException {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UtenteNotFoundException("User not found with email: " + email));
    }

    public Utente getUtente(Long id) throws UtenteNotFoundException {
        return utenteRepository.findById(id).orElseThrow(() -> new UtenteNotFoundException("Utente not found"));
    }

    public Utente createUtente(UtenteDto utenteDto) {
        Utente utente = new Utente();
        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword())); // Utilizzo del PasswordEncoder
        utente.setFirstName(utenteDto.getFirstName());
        utente.setLastName(utenteDto.getLastName());
        utente.setCodiceFiscale(utenteDto.getCodiceFiscale());
        utente.setRuolo("USER");
        utente.setCitta(utenteDto.getCitta());
        return utenteRepository.save(utente);
    }

    public Utente updateUtente(Long id, UtenteDto utenteDto) throws UtenteNotFoundException, CarrelloNotFoundException {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new UtenteNotFoundException("Utente not found"));
        utente.setFirstName(utenteDto.getFirstName());
        utente.setLastName(utenteDto.getLastName());
        utente.setUsername(utenteDto.getUsername());
        utente.setEmail(utenteDto.getEmail());
        utente.setCitta(utenteDto.getCitta());
        utente.setCodiceFiscale(utenteDto.getCodiceFiscale());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword())); // Utilizzo del PasswordEncoder
        utente.setRuolo(utenteDto.getRuolo());
        List<Carrello> carrelli = new ArrayList<>();
        for (CarrelloDto carrelloDto : utenteDto.getCarrelliDto()) {
            Carrello carrello = carrelloService.getCarrello(carrelloDto.getId());
            carrelli.add(carrello);
        }
        utente.setCarrelli(carrelli);
        return utenteRepository.save(utente);
    }

    public void deleteUtente(Long id) throws UtenteNotFoundException {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new UtenteNotFoundException("Utente not found"));
        utenteRepository.delete(utente);
    }
}




