package com.desafio.forohub.infra.service;

import com.aluracursos.forohub.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Challenge")
                    .withSubject(user)
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateDueDate())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Failed to generate token");
        }
    }

    public String getSubject(String token){
        if (token == null){
            throw new RuntimeException("Token is null");
        }

        DecodedJWT verifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Challenge")
                    .build()
                    .verify(token);
            verifier.getSubject();

        }catch (JWTVerificationException e){
            System.out.println(e.toString());
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("Invalid verifier");
        }
        return verifier.getSubject();
    }

    private Instant generateDueDate() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-06:00"));
    }
}