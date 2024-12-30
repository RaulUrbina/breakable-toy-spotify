package com.spark.server.service;

import com.spark.server.model.SpotifyArtistResponse;
import com.spark.server.util.SpotifyEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {
    private final RestTemplate restTemplate = new RestTemplate();;

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


}
