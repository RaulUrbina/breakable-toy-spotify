package com.spark.server.service;

import com.spark.server.config.SpotifyProperties;
import com.spark.server.model.SpotifyTokenResponse;
import com.spark.server.token.TokenData;
import com.spark.server.token.TokenStore;
import com.spark.server.util.SpotifyEndpoints;
import static com.spark.server.util.RandomStringGenerator.generateRandomString;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SpotifyProperties spotifyProperties;
    private final SpotifyApiService spotifyApiService;
    private final TokenStore tokenStore;

    //Generates Spotify's URL for Auth process
    public void redirectToSpotifyLogin(HttpServletResponse response) throws IOException {
        String state = generateRandomString();
        String scope = "user-read-private user-read-email user-top-read";

        String spotifyAuthUrl = SpotifyEndpoints.AUTHORIZE_URL + "?" +
                "response_type=code&" +
                "client_id=" + spotifyProperties.getClientId() + "&" +
                "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                "redirect_uri=" + URLEncoder.encode(spotifyProperties.getRedirectUri(), StandardCharsets.UTF_8) + "&" +
                "state=" + state;

        response.sendRedirect(spotifyAuthUrl);
    }

    //Trades the login code returned from the Auth process for an access and refresh token
    public String exchangeCodeForToken(String code) {
        SpotifyTokenResponse tokenResponse = spotifyApiService.exchangeCodeForToken(code);

        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
            String accessToken = tokenResponse.getAccessToken();
            String refreshToken = tokenResponse.getRefreshToken();
            long expiresIn = tokenResponse.getExpiresIn();

            String sessionId = UUID.randomUUID().toString();
            tokenStore.storeTokens(sessionId, accessToken, refreshToken, expiresIn);

            return sessionId;
        } else {
            throw new RuntimeException("No access token found in the response.");
        }
    }

    //Refreshes the access token
    public String refreshAccessToken(String sessionId) {
        TokenData tokenData = tokenStore.getTokens(sessionId);

        if (tokenData == null || tokenData.getRefreshToken() == null) {
            throw new IllegalArgumentException("No token found for session: " + sessionId);
        }

        SpotifyTokenResponse tokenResponse = spotifyApiService.refreshAccessToken(tokenData.getRefreshToken());

        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
            String newAccessToken = tokenResponse.getAccessToken();
            long expiresIn = tokenResponse.getExpiresIn();

            tokenData.setAccessToken(newAccessToken);
            tokenData.setExpiresAt(Instant.now().plusSeconds(expiresIn));
            tokenStore.storeTokens(sessionId, newAccessToken, tokenData.getRefreshToken(), expiresIn);

            return newAccessToken;
        } else {
            throw new RuntimeException("Failed to refresh access token for session: " + sessionId);
        }
    }

    //Retrieves the access token based on the sessionId, refreshes it if needed
    public String getValidAccessToken(String sessionId) {
        TokenData tokenData = tokenStore.getTokens(sessionId);

        if (tokenData == null) {
            throw new RuntimeException("No token found for the session " + sessionId);
        }

        if (!tokenStore.isAccessTokenValid(sessionId)) {
            return refreshAccessToken(sessionId);
        }

        return tokenData.getAccessToken();
    }

    public void getClientTokens(){
        SpotifyTokenResponse tokenResponse = spotifyApiService.getClientTokens();

        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
            String accessToken = tokenResponse.getAccessToken();
            String refreshToken = tokenResponse.getRefreshToken();
            long expiresIn = tokenResponse.getExpiresIn();

            String sessionId = UUID.randomUUID().toString();
            tokenStore.storeTokens(sessionId, accessToken, refreshToken, expiresIn);

        } else {
            throw new RuntimeException("No access token found in the response.");
        }
    }

}
