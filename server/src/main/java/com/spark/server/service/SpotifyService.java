package com.spark.server.service;

import com.spark.server.dto.ArtistDTO;
import com.spark.server.map.ArtistMapper;
import com.spark.server.model.SpotifyArtistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApiService spotifyApiService;
    private final AuthService authService;
    private final ArtistMapper artistMapper;

    public List<ArtistDTO> getUserTopArtists(String sessionId) {

        String accessToken = authService.getValidAccessToken(sessionId);

        SpotifyArtistResponse response = spotifyApiService.getUserTopArtists(accessToken);

        return artistMapper.mapToArtistDTOs(response.getItems());

    }

}
