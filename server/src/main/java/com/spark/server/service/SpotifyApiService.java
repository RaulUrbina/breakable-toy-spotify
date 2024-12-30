package com.spark.server.service;

import com.spark.server.config.SpotifyProperties;
import com.spark.server.model.SpotifyArtistResponse;
import com.spark.server.model.SpotifyTokenResponse;
import com.spark.server.util.SpotifyEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final SpotifyProperties spotifyProperties;

    public SpotifyTokenResponse exchangeCodeForToken(String code) {

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
            ResponseEntity<SpotifyTokenResponse> response = restTemplate.exchange(
                    SpotifyEndpoints.TOKEN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    SpotifyTokenResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while exchanging the code: " + e.getMessage(), e);
        }
    }


    public SpotifyArtistResponse getUserTopArtists(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SpotifyArtistResponse> response = restTemplate.exchange(
                    SpotifyEndpoints.TOP_ARTISTS, HttpMethod.GET, request, SpotifyArtistResponse.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    public SpotifyTokenResponse refreshAccessToken(String refreshToken) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        body.add("client_id", spotifyProperties.getClientId());
        body.add("client_secret", spotifyProperties.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<SpotifyTokenResponse> response = restTemplate.exchange(
                    SpotifyEndpoints.TOKEN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    SpotifyTokenResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing the token: " + e.getMessage(), e);
        }
    }
}
