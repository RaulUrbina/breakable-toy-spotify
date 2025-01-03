package com.spark.server.controller;

import com.spark.server.dto.AlbumPreviewDTO;
import com.spark.server.dto.ApiResponse;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.dto.TrackDTO;
import com.spark.server.model.SpotifyAlbumDetailResponse;
import com.spark.server.model.SpotifyTrackResponse;
import com.spark.server.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final SpotifyService spotifyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArtistDetailDTO>> getArtistDetails(
            @PathVariable("id") String artistId,
            @RequestHeader("Session-Id") String sessionId) {

        ArtistDetailDTO artistDetail = spotifyService.getArtistDetails(sessionId, artistId);

        ApiResponse<ArtistDetailDTO> response = new ApiResponse<>(
                true,
                "Artist details retrieved successfully.",
                artistDetail
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/top-tracks")
    public ResponseEntity<ApiResponse<List<TrackDTO>>> getTopTracks(
            @PathVariable("id") String artistId,
            @RequestHeader("Session-Id") String sessionId) {

        List<TrackDTO> artistDetail = spotifyService.getArtistTopTracks(sessionId, artistId);

        ApiResponse<List<TrackDTO>> response = new ApiResponse<>(
                true,
                "Artist tracks retrieved successfully.",
                artistDetail
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<ApiResponse<List<AlbumPreviewDTO>>> getArtistAlbums(
            @PathVariable("id") String artistId,
            @RequestHeader("Session-Id") String sessionId) {

        List<AlbumPreviewDTO> albumPreviewList = spotifyService.getArtistAlbums(sessionId, artistId);

        ApiResponse<List<AlbumPreviewDTO>> response = new ApiResponse<>(
                true,
                "Artist tracks retrieved successfully.",
                albumPreviewList
        );

        return ResponseEntity.ok(response);

    }
}
