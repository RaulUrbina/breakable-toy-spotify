package com.spark.server.controller;

import com.spark.server.dto.ApiResponse;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.model.SpotifyAlbumDetailResponse;
import com.spark.server.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final SpotifyService spotifyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpotifyAlbumDetailResponse>> getAlbumDetails(
            @PathVariable("id") String albumId,
            @RequestHeader("Session-Id") String sessionId) {

        SpotifyAlbumDetailResponse albumDetailResponse = spotifyService.getAlbumDetails(sessionId, albumId);

        ApiResponse<SpotifyAlbumDetailResponse> response = new ApiResponse<>(
                true,
                "Artist details retrieved successfully.",
                albumDetailResponse
        );

        return ResponseEntity.ok(response);
    }

}
