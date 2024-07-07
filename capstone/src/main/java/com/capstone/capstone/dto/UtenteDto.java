package com.capstone.capstone.dto;


import lombok.Data;

import java.util.List;

@Data
public class UtenteDto {
    private Long id;
    private String  firstName;
    private String lastName;
    private String username;
    private String email;
    private String citta;
    private String codiceFiscale;
    private String password;
    private List<CarrelloDto> carrelliDto;
    private String ruolo;

}
