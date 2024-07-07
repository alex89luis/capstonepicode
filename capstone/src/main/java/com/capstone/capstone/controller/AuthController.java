package com.capstone.capstone.controller;

import com.capstone.capstone.dto.UtenteDto;
import com.capstone.capstone.dto.UtenteLoginDto;
import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.EmailAlreadyInUseException;
import com.capstone.capstone.exception.UnauthorizedException;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.service.AuthService;
import com.capstone.capstone.service.UtenteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;



    @Autowired
    private AuthService authService;






    @PostMapping("/auth/registerUtente")
    public String registerUtente(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }


        utenteService.createUtente(utenteDto);
        return "Utente with email " + utenteDto.getEmail() + " has been created!";
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UtenteLoginDto utenteLoginDTO, BindingResult bindingResult) throws BadRequestException, UtenteNotFoundException, UnauthorizedException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).
                    reduce("", (s, s2) -> s + s2));
        }
        return authService.authenticateUtenteAndCreateToken(utenteLoginDTO);
    }


}


