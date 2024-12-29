package com.spark.server.controller;

import com.spark.server.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth/spotify")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
        authService.redirectToSpotifyLogin(response);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    public String exchangeCodeForToken(String code) {
        return "accessToken";
    }


}
