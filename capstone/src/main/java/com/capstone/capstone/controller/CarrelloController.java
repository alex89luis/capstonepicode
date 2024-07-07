package com.capstone.capstone.controller;

import com.capstone.capstone.dto.CarrelloDto;
import com.capstone.capstone.entity.Carrello;
import com.capstone.capstone.exception.CarrelloNotFoundException;
import com.capstone.capstone.exception.ProdottoNotFoundException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrelli")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    @GetMapping
    public List<Carrello> getAllCarrelli() {
        return carrelloService.getAllCarrelli();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrello> getCarrello(@PathVariable Long id) throws CarrelloNotFoundException {
        Carrello carrello = carrelloService.getCarrello(id);
        return ResponseEntity.ok(carrello);
    }

    @PostMapping
    public ResponseEntity<Carrello> createCarrello(@RequestBody CarrelloDto carrelloDto) throws UtenteNotFoundException, ProdottoNotFoundException {
        Carrello carrello = carrelloService.createCarrello(carrelloDto);
        return ResponseEntity.ok(carrello);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrello> updateCarrello(@PathVariable Long id, @RequestBody CarrelloDto carrelloDto) throws CarrelloNotFoundException, ProdottoNotFoundException {
        Carrello updatedCarrello = carrelloService.updateCarrello(id, carrelloDto);
        return ResponseEntity.ok(updatedCarrello);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrello(@PathVariable Long id) throws CarrelloNotFoundException {
        carrelloService.deleteCarrello(id);
        return ResponseEntity.noContent().build();
    }
}
