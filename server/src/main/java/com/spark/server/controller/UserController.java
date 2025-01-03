package com.spark.server.controller;

import com.spark.server.dto.ApiResponse;
import com.spark.server.dto.ArtistDTO;
import com.spark.server.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {

    private final SpotifyService spotifyService;

    @GetMapping("/top/artists")
    public ResponseEntity<ApiResponse<List<ArtistDTO>>> getTopArtists(@RequestHeader("Session-Id") String sessionId) {
        try {
            List<ArtistDTO> topArtists = spotifyService.getUserTopArtists(sessionId);
            ApiResponse<List<ArtistDTO>> response = new ApiResponse<>(
                    true,
                    "Top artists retrieved successfully.",
                    topArtists
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, ex.getMessage(), null)
            );
        }
    }



}
