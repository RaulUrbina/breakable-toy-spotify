package com.spark.server.service;

import com.spark.server.config.SpotifyProperties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.spark.server.util.RandomStringGenerator.generateRandomString;

@Service
public class AuthService {

    private final SpotifyProperties spotifyProperties;
    private final RestTemplate restTemplate;

    public AuthService(SpotifyProperties spotifyProperties) {
        this.spotifyProperties = spotifyProperties;
        this.restTemplate = new RestTemplate();
    }

    public void redirectToSpotifyLogin(HttpServletResponse response) throws IOException {
        String state = generateRandomString();
        String scope = "user-read-private user-read-email";

        String spotifyAuthUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code&" +
                "client_id=" + spotifyProperties.getClientId() + "&" +
                "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                "redirect_uri=" + URLEncoder.encode(spotifyProperties.getRedirectUri(), StandardCharsets.UTF_8) + "&" +
                "state=" + state;

        response.sendRedirect(spotifyAuthUrl);
    }

    public String exchangeCodeForToken(String code) {
        String tokenUrl = "https://accounts.spotify.com/api/token";

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
                    tokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return (String) responseBody.get("access_token");
            } else {
                throw new RuntimeException("No access token found in the response.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while exchanging the code: " + e.getMessage(), e);
        }
    }


}
