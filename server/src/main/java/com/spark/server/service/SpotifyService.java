package com.spark.server.service;

import com.spark.server.dto.AlbumPreviewDTO;
import com.spark.server.dto.ArtistDTO;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.dto.TrackDTO;
import com.spark.server.map.ArtistMapper;
import com.spark.server.map.TrackMapper;
import com.spark.server.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApiService spotifyApiService;
    private final AuthService authService;
    private final ArtistMapper artistMapper;
    private final TrackMapper trackMapper;

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

    public List<TrackDTO> getArtistTopTracks(String sessionId, String albumId) {
        String accessToken = authService.getValidAccessToken(sessionId);

        SpotifyTrackResponse response = spotifyApiService.getArtistTopTracks(albumId, accessToken);

        return trackMapper.mapToTrackDTOs(response.getTracks());

    }

    public List<AlbumPreviewDTO> getArtistAlbums(String sessionId, String artistId) {
        String accessToken = authService.getValidAccessToken(sessionId);

        SpotifyArtistAlbumsResponse artistAlbumsResponse = spotifyApiService.getArtistAlbums(artistId, accessToken);

        return artistAlbumsResponse.getAlbums().stream()
                .map(album -> {
                    AlbumPreviewDTO dto = new AlbumPreviewDTO();
                    dto.setId(album.getId());
                    dto.setName(album.getName());
                    dto.setSmallImage(album.getSmallImage());
                    dto.setMediumImage(album.getMediumImage());
                    dto.setLargeImage(album.getLargeImage());

                    if (album.getArtists() != null && !album.getArtists().isEmpty()) {
                        dto.setArtistId(album.getArtists().get(0).getId());
                        dto.setArtistName(album.getArtists().get(0).getName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

    }

    public SpotifySearchResponse search(String sessionId, String query) {
        String accessToken = authService.getValidAccessToken(sessionId);
        return spotifyApiService.search(query, "artist,album,track", accessToken);
    }






}
