package com.capstone.capstone.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="carrelli")
public class Carrello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(
            name="prodotti_carrello",
            joinColumns = @JoinColumn(name="carrello_id"),
            inverseJoinColumns = @JoinColumn(name="prodotto_id")

    )
    private List<Prodotto> prodotti;
    private double totale;
    @ManyToOne
    @JoinColumn(name="utente_id")
    private Utente utente;
}