package com.capstone.capstone.dto;

import com.capstone.capstone.entity.Utente;
import lombok.Data;

import java.util.List;


@Data
public class CarrelloDto {
    private Long id;
    private List<Long> prodotti; // Lista di identificatori di prodotti
    private double totale;
    private Long  utenteId; // Identificatore dell'utente
}


