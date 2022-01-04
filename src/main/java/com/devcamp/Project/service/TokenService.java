package com.devcamp.Project.service;

import com.devcamp.Project.entity.Token;

public interface TokenService {
    Token createToken(Token token);
    Token findByToken(String token);

}
