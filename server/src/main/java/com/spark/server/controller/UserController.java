package com.spark.server.controller;

import com.spark.server.dto.ArtistDTO;
import com.spark.server.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {

    private final SpotifyService spotifyService;

    @GetMapping("/top/artists")
    public ResponseEntity<List<ArtistDTO>> getTopArtists(@RequestHeader("Client-Id") String clientId) {
        try {
            List<ArtistDTO> topArtists = spotifyService.getUserTopArtists(clientId);
            return ResponseEntity.ok(topArtists);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



}
