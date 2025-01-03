package com.spark.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.server.config.SpotifyProperties;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.model.*;
import com.spark.server.util.SpotifyEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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

    public SpotifyTokenResponse getClientTokens() {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
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
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token is missing or invalid.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SpotifyArtistResponse> response = restTemplate.exchange(
                    SpotifyEndpoints.TOP_ARTISTS,
                    HttpMethod.GET,
                    request,
                    SpotifyArtistResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Spotify API error: " + response.getStatusCode() + " - " + response.getBody());
            }

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    public SpotifyTokenResponse refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is missing or invalid.");
        }

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

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Spotify API error: " + response.getStatusCode() + " - " + response.getBody());
            }

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing the token: " + e.getMessage(), e);
        }
    }

    public SpotifyArtistDetailResponse getArtistDetails(String artistId, String accessToken) {
        String url = SpotifyEndpoints.ARTIST_DETAILS.replace("{id}", artistId);
        if (artistId == null || artistId.isEmpty()) {
            throw new IllegalArgumentException("Artist ID is required.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SpotifyArtistDetailResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SpotifyArtistDetailResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Spotify API error: " + response.getStatusCode());
            }

            SpotifyArtistDetailResponse responseBody = response.getBody();
            if (responseBody == null) {
                throw new RuntimeException("Spotify API returned an empty response.");
            }

            return responseBody;

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching artist details: " + e.getMessage(), e);
        }
    }

    public SpotifyTrackResponse getArtistTopTracks(String artistId, String accessToken) {
        if (artistId == null || artistId.isEmpty()) {
            throw new IllegalArgumentException("Artist ID is required.");
        }

        String url = SpotifyEndpoints.ARTIST_TOP_TRACKS.replace("{id}", artistId) + "?market=US";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SpotifyTrackResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SpotifyTrackResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Spotify API error: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching artist's top tracks: " + e.getMessage(), e);
        }
    }

    public SpotifyAlbumDetailResponse getAlbumDetails(String albumId, String accessToken) {
        String url = SpotifyEndpoints.ALBUM_DETAILS.replace("{id}", albumId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), SpotifyAlbumDetailResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching album details: " + e.getMessage(), e);
        }
    }

    public SpotifyArtistAlbumsResponse getArtistAlbums(String artistId, String accessToken) {
        if (artistId == null || artistId.isEmpty()) {
            throw new IllegalArgumentException("Artist ID is required.");
        }

        String url = SpotifyEndpoints.ARTIST_ALBUMS.replace("{id}", artistId) + "?include_groups=album,single,compilation&limit=20";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<SpotifyArtistAlbumsResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SpotifyArtistAlbumsResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Spotify API error: " + response.getStatusCode());
            }

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching artist's albums: " + e.getMessage(), e);
        }
    }

    public SpotifySearchResponse search(String query, String type, String accessToken) {
        String url = SpotifyEndpoints.SEARCH + "?q=" + query + "&type=" + type + "&limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<SpotifySearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                SpotifySearchResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Spotify API error: " + response.getStatusCode());
        }

        System.out.println("Response: " + response.getBody());

        return response.getBody();
    }






}
