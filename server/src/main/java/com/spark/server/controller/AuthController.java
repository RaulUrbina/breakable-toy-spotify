package com.spark.server.controller;

import com.spark.server.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth/spotify")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //Login Endpoint
    @GetMapping
    public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
        authService.redirectToSpotifyLogin(response);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    //Internal called callback endpoint
    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        try {
            String sessionId = authService.exchangeCodeForToken(code);
            response.sendRedirect("http://localhost:3000/?userId=" + sessionId);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            response.sendRedirect("http://localhost:3000/?error=true");
            return ResponseEntity.internalServerError().build();
        }
    }


}
