package br.com.fiap.fin_money_api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.fin_money_api.model.Token;
import br.com.fiap.fin_money_api.model.User;

@Service
public class TokenService {

    // Define a data de expiração do token para 1 dia após a criação (ajustado para o fuso horário GMT-3)
    private Instant expiresAt = LocalDateTime.now()
        .plusDays(1)
        .toInstant(ZoneOffset.ofHours(-3));

    // Algoritmo de assinatura HMAC256 com chave secreta fixa (ideal usar em variáveis de ambiente)
    private Algorithm algorithm = Algorithm.HMAC256("secret");
    
    /**
     * Gera um token JWT com base nas informações do usuário.
     * @param user Usuário autenticado
     * @return Objeto Token contendo o JWT gerado e o e-mail do usuário
     */
    public Token createToken(User user){
        var jwt = JWT.create()
            .withSubject(user.getId().toString()) // Define o "sub" (identificador principal) como o ID do usuário
            .withClaim("email", user.getEmail())  // Adiciona e-mail como informação extra (claim)
            .withExpiresAt(expiresAt)             // Define o tempo de expiração do token
            .sign(algorithm);                     // Assina o token usando o algoritmo definido

        return new Token(jwt, user.getEmail());
    }

    /**
     * Extrai os dados do usuário a partir de um token JWT válido.
     * @param token Token JWT a ser validado e interpretado
     * @return Objeto User reconstruído a partir das informações do token
     */
    public User getUserFromToken(String token){
        var verifiedToken = JWT.require(algorithm)
            .build()
            .verify(token); // Valida assinatura e expiração

        return User.builder()
                .id(Long.valueOf(verifiedToken.getSubject())) // Recupera o "sub" como ID do usuário
                .email(verifiedToken.getClaim("email").toString()) // Recupera o e-mail do claim
                .build();
    }

}
