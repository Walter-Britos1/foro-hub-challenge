package com.aluracursos.forohub.controllers;

import com.aluracursos.forohub.domain.user.User;
import com.aluracursos.forohub.domain.user.dto.AuthenticationUserDTO;
import com.aluracursos.forohub.infra.security.dto.JWTDTO;
import com.desafio.forohub.infra.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticacion", description = "Genera el token del usuario para el acceso")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTDTO> autenticateUser(@RequestBody @Valid AuthenticationUserDTO authenticationUserDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(authenticationUserDTO.username(),
                authenticationUserDTO.password());

        var userAuthenticated = authenticationManager.authenticate(authToken);
        var JWT = tokenService.generateToken((User) userAuthenticated.getPrincipal());

        return ResponseEntity.ok(new JWTDTO(JWT));
    }
}
