package com.capstone.capstone.service;


import com.capstone.capstone.dto.UtenteLoginDto;
import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.UnauthorizedException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public String authenticateUtenteAndCreateToken(UtenteLoginDto utenteLoginDTO) throws UnauthorizedException, UtenteNotFoundException {
        Utente utente = utenteService.getUserByEmail(utenteLoginDTO.getEmail());
        if (utente != null) {

            if (passwordEncoder.matches(utenteLoginDTO.getPassword(), utente.getPassword())) {

                return jwtTool.createToken(utente);
            } else {

                throw new UnauthorizedException("Error in authorization, relogin!");
            }
        } else {

            throw new UtenteNotFoundException("utente with email " + utenteLoginDTO.getEmail() + " not found.");
        }
    }
}
