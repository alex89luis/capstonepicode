package com.capstone.capstone.controller;

import com.capstone.capstone.dto.CarrelloDto;
import com.capstone.capstone.dto.UtenteDto;
import com.capstone.capstone.entity.Carrello;
import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.CarrelloNotFoundException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.repository.UtenteRepository;
import com.capstone.capstone.service.CarrelloService;
import com.capstone.capstone.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private CarrelloService carrelloService;

    @GetMapping
    public List<Utente> getAllUtenti() {
        return utenteService.getAllUtenti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtente(@PathVariable Long id) throws UtenteNotFoundException {
        Utente utente = utenteService.getUtente(id);
        return ResponseEntity.ok(utente);
    }
    @GetMapping("byEmail/{email}")
    public ResponseEntity<Utente> getUtenteByEmail(@PathVariable String email) throws UtenteNotFoundException {
        Utente utente = utenteService.getUserByEmail(email);
        return ResponseEntity.ok(utente);
    }

    @PostMapping
    public Utente createUtente(@RequestBody UtenteDto utenteDto) {
        return utenteService.createUtente(utenteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> updateUtente(@PathVariable Long id, @RequestBody UtenteDto utenteDto) throws UtenteNotFoundException, CarrelloNotFoundException {
        Utente updatedUtente = utenteService.updateUtente(id, utenteDto);
        return ResponseEntity.ok(updatedUtente);
    }
    public Utente updateCarrelli(Long id, List<CarrelloDto> carrelliDto) throws UtenteNotFoundException, CarrelloNotFoundException {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new UtenteNotFoundException("Utente not found"));

        List<Carrello> carrelli = new ArrayList<>();
        for (CarrelloDto carrelloDto : carrelliDto) {
            Carrello carrello = carrelloService.getCarrello(carrelloDto.getId());
            carrelli.add(carrello);
        }
        utente.setCarrelli(carrelli);

        return utenteRepository.save(utente);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) throws UtenteNotFoundException {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }

}