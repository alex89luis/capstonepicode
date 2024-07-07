package com.capstone.capstone.repository;

import com.capstone.capstone.entity.Carrello;
import com.capstone.capstone.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrelloRepository extends JpaRepository<Carrello,Long> {
}
