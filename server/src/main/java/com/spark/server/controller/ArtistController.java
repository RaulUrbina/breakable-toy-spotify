package com.spark.server.controller;

import com.spark.server.dto.ApiResponse;
import com.spark.server.dto.ArtistDetailDTO;
import com.spark.server.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
