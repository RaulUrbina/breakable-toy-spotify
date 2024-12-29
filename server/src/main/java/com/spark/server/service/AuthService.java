package com.spark.server.service;

import com.spark.server.config.SpotifyProperties;
import com.spark.server.token.TokenData;
import com.spark.server.token.TokenStore;
import com.spark.server.util.SpotifyEndpoints;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static com.spark.server.util.RandomStringGenerator.generateRandomString;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SpotifyProperties spotifyProperties;
    private final TokenStore tokenStore;
    private final TokenRefreshService tokenRefreshService;
    private final RestTemplate restTemplate = new RestTemplate();

    public void redirectToSpotifyLogin(HttpServletResponse response) throws IOException {
        String state = generateRandomString();
        String scope = "user-read-private user-read-email";

        String spotifyAuthUrl = SpotifyEndpoints.AUTHORIZE_URL + "?" +
                "response_type=code&" +
                "client_id=" + spotifyProperties.getClientId() + "&" +
                "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                "redirect_uri=" + URLEncoder.encode(spotifyProperties.getRedirectUri(), StandardCharsets.UTF_8) + "&" +
                "state=" + state;

        response.sendRedirect(spotifyAuthUrl);
    }

    public String exchangeCodeForToken(String code) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", spotifyProperties.getRedirectUri());
        body.add("client_id", spotifyProperties.getClientId());
        body.add("client_secret", spotifyProperties.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    SpotifyEndpoints.TOKEN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map responseBody = response.getBody();
            System.out.println("Response Body: " + responseBody);
            if (responseBody != null && responseBody.containsKey("access_token")) {
                String accessToken = (String) responseBody.get("access_token");
                String refreshToken = (String) responseBody.get("refresh_token");
                long expiresIn = ((Number) responseBody.get("expires_in")).longValue();

                String clientId = UUID.randomUUID().toString();
                tokenStore.storeTokens(clientId, accessToken, refreshToken, expiresIn);
                return clientId;
            } else {
                throw new RuntimeException("No access token found in the response.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while exchanging the code: " + e.getMessage(), e);
        }
    }

    public String getValidAccessToken(String clientId) {
        TokenData tokenData = tokenStore.getTokens(clientId);

        if (tokenData == null) {
            throw new RuntimeException("No token found for the client " + clientId);
        }

        if (!tokenStore.isAccessTokenValid(clientId)) {
            return tokenRefreshService.refreshAccessToken(clientId);
        }

        return tokenData.getAccessToken();
    }

}
