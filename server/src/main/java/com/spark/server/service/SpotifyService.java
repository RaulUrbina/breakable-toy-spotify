package com.spark.server.service;

import com.spark.server.dto.ArtistDTO;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.map.ArtistMapper;
import com.spark.server.model.SpotifyAlbumDetailResponse;
import com.spark.server.model.SpotifyArtistDetailResponse;
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

    public ArtistDetailDTO getArtistDetails(String sessionId, String artistId) {
        String accessToken = authService.getValidAccessToken(sessionId);

        SpotifyArtistDetailResponse response = spotifyApiService.getArtistDetails(artistId, accessToken);
        return artistMapper.mapToArtistDetailDTO(response);
    }

    public SpotifyAlbumDetailResponse getAlbumDetails(String sessionId, String albumId) {
        String accessToken = authService.getValidAccessToken(sessionId);

        return spotifyApiService.getAlbumDetails(albumId, accessToken);
    }




}
