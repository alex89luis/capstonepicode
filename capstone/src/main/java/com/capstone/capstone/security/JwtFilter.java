package com.capstone.capstone.security;

import com.capstone.capstone.entity.Utente;
import com.capstone.capstone.exception.UtenteNotFoundException;
import com.capstone.capstone.service.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private UtenteService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleUnauthorized(response, "Error in authorization, token missing or does not start with 'Bearer '!");
            return;
        }

        String token = authHeader.substring(7);

        try {
            jwtTool.verifyToken(token);
        } catch (Exception e) {
            handleUnauthorized(response, "Invalid token!");
            return;
        }

        String utenteEmail = jwtTool.getEmailFromToken(token);

        Utente utente;
        try {
            utente = userService.getUserByEmail(utenteEmail);
        } catch (UtenteNotFoundException e) {
            logger.error("User with email=" + utenteEmail + " not found");
            handleUnauthorized(response, "User with email=" + utenteEmail + " not found");
            return;
        }

        if (utente != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathRequestMatcher("/auth/**").matches(request);
    }

    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}