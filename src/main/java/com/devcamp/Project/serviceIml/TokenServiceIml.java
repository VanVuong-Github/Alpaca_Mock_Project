package com.devcamp.Project.serviceIml;

import com.devcamp.Project.entity.Token;
import com.devcamp.Project.repository.TokenRepository;
import com.devcamp.Project.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceIml implements TokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token createToken(Token token) {

        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
