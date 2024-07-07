package com.capstone.capstone.controller;

import com.capstone.capstone.entity.Prodotto;
import com.capstone.capstone.exception.ProdottoNotFoundException;
import com.capstone.capstone.service.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping
    public List<Prodotto> getAllProdotti() {
        return prodottoService.getAllProdotti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prodotto> getProdotto(@PathVariable Long id) throws ProdottoNotFoundException {
        Prodotto prodotto = prodottoService.getProdotto(id);
        return ResponseEntity.ok(prodotto);
    }

    @PostMapping
    public Prodotto createProdotto(@RequestBody Prodotto prodotto) {
        return prodottoService.createProdotto(prodotto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prodotto> updateProdotto(@PathVariable Long id, @RequestBody Prodotto prodottoDetails) throws ProdottoNotFoundException {
        Prodotto updatedProdotto = prodottoService.updateProdotto(id, prodottoDetails);
        return ResponseEntity.ok(updatedProdotto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdotto(@PathVariable Long id) throws ProdottoNotFoundException {
        prodottoService.deleteProdotto(id);
        return ResponseEntity.noContent().build();
    }
}

