package com.capstone.capstone.dto;


import lombok.Data;

import java.util.List;
@Data
public class ProdottoDto {
    private Long id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private String immagine;

}
