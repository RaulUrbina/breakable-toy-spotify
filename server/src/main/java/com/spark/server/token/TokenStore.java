package com.spark.server.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TokenStore {
    private final ConcurrentHashMap<String, TokenData> tokenStore = new ConcurrentHashMap<>();

    public void storeTokens(String clientId, String accessToken, String refreshToken, long expiresIn) {
        TokenData tokenData = new TokenData(
                accessToken,
                refreshToken,
                Instant.now().plusSeconds(expiresIn)
        );
        tokenStore.put(clientId, tokenData);
    }

    public TokenData getTokens(String clientId) {
        return tokenStore.get(clientId);
    }

    public boolean isAccessTokenValid(String clientId) {
        TokenData tokenData = tokenStore.get(clientId);
        return tokenData != null && Instant.now().isBefore(tokenData.getExpiresAt());
    }

}
