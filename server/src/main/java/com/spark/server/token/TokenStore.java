package com.spark.server.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TokenStore {
    private final ConcurrentHashMap<String, TokenData> tokenStore = new ConcurrentHashMap<>();

    public void storeTokens(String sessionId, String accessToken, String refreshToken, long expiresIn) {
        TokenData tokenData = new TokenData(
                accessToken,
                refreshToken,
                Instant.now().plusSeconds(expiresIn)
        );
        tokenStore.put(sessionId, tokenData);
    }

    public TokenData getTokens(String sessionId) {
        return tokenStore.get(sessionId);
    }

    public boolean isAccessTokenValid(String sessionId) {
        TokenData tokenData = tokenStore.get(sessionId);
        return tokenData != null && Instant.now().isBefore(tokenData.getExpiresAt());
    }

}
