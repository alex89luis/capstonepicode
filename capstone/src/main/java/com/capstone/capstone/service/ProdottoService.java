package com.capstone.capstone.service;

import com.capstone.capstone.entity.Prodotto;
import com.capstone.capstone.exception.ProdottoNotFoundException;
import com.capstone.capstone.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public List<Prodotto> getAllProdotti() {
        return prodottoRepository.findAll();
    }

    public Prodotto getProdotto(Long id) throws ProdottoNotFoundException {
        return prodottoRepository.findById(id).orElseThrow(() -> new ProdottoNotFoundException("Prodotto not found"));
    }

    public Prodotto createProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public Prodotto updateProdotto(Long id, Prodotto prodottoDetails) throws ProdottoNotFoundException {
        Prodotto prodotto = prodottoRepository.findById(id).orElseThrow(() -> new ProdottoNotFoundException("Prodotto not found"));
        prodotto.setNome(prodottoDetails.getNome());
        prodotto.setDescrizione(prodottoDetails.getDescrizione());
        prodotto.setPrezzo(prodottoDetails.getPrezzo());
        prodotto.setImmagine(prodottoDetails.getImmagine());
        prodotto.setCarrelli(prodottoDetails.getCarrelli());
        return prodottoRepository.save(prodotto);
    }

    public void deleteProdotto(Long id) throws ProdottoNotFoundException {
        Prodotto prodotto = prodottoRepository.findById(id).orElseThrow(() -> new ProdottoNotFoundException("Prodotto not found"));
        prodottoRepository.delete(prodotto);
    }
}
