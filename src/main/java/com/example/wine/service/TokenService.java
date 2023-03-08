package com.example.wine.service;

import com.example.wine.entity.Blacklist;
import com.example.wine.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    @Transactional
    public void logout(String token) {
        existedBlackList(token);
        tokenRepository.save(Blacklist.builder().token(token).build());
    }

    public boolean checkBlacklist(String token) {
        return tokenRepository.existsByToken(token);
    }

    public void existedBlackList(String token) {
        if (checkBlacklist(token)) {
            tokenRepository.delete(tokenRepository.findByToken(token));
        }
    }
}