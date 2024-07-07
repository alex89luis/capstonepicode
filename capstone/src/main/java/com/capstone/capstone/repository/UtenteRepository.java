package com.capstone.capstone.repository;

import com.capstone.capstone.entity.Prodotto;
import com.capstone.capstone.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,Long> {
    public Optional<Utente> findByEmail(String email);
}
