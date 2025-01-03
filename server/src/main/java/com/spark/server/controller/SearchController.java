package com.spark.server.controller;

import com.spark.server.dto.AlbumPreviewDTO;
import com.spark.server.dto.ApiResponse;
import com.spark.server.model.SpotifySearchResponse;
import com.spark.server.service.SpotifyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SpotifyService spotifyService;


        @GetMapping
        public ResponseEntity<ApiResponse<SpotifySearchResponse>> search(
                @RequestHeader("Session-Id") String sessionId,
                @RequestParam("q") String query
        ) {
            System.out.println("Hola");
            SpotifySearchResponse data = spotifyService.search(sessionId, query);

            ApiResponse<SpotifySearchResponse> response = new ApiResponse<>(
                    true,
                    "Artist tracks retrieved successfully.",
                    data
            );

            return ResponseEntity.ok(response);
        }
}
