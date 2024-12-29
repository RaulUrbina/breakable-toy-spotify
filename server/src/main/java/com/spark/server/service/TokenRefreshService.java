package com.spark.server.service;

import com.spark.server.config.SpotifyProperties;
import com.spark.server.token.TokenData;
import com.spark.server.token.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private final TokenStore tokenStore;
    private final SpotifyProperties spotifyProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public String refreshAccessToken(String clientId) {
        TokenData tokenData = tokenStore.getTokens(clientId);

        if (tokenData == null || tokenData.getRefreshToken() == null) {
            throw new RuntimeException("No token found for: " + clientId);
        }

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", tokenData.getRefreshToken());
        body.add("client_id", spotifyProperties.getClientId());
        body.add("client_secret", spotifyProperties.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://accounts.spotify.com/api/token",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                String newAccessToken = (String) responseBody.get("access_token");
                long expiresIn = ((Number) responseBody.get("expires_in")).longValue();

                tokenData.setAccessToken(newAccessToken);
                tokenData.setExpiresAt(Instant.now().plusSeconds(expiresIn));
                tokenStore.storeTokens(clientId, tokenData.getAccessToken(), tokenData.getRefreshToken(), expiresIn);

                return newAccessToken;
            } else {
                throw new RuntimeException("No token found in the response.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing the token: " + e.getMessage(), e);
        }
    }


}
