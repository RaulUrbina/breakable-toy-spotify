package com.spark.server.AuthController;

import com.spark.server.config.SpotifyProperties;
import com.spark.server.util.RandomStringGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Map;

@RestController
@RequestMapping("/auth/spotify")
public class SpotifyAuthController {

    private final SpotifyProperties spotifyProperties;

    public SpotifyAuthController(SpotifyProperties spotifyProperties) {
        this.spotifyProperties = spotifyProperties;
    }

    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/login")
    public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
        String state = RandomStringGenerator.generateRandomString();
        String scope = "user-read-private user-read-email";
        System.out.println("Client id: " + spotifyProperties.getClientId());
        String spotifyAuthUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code&" +
                "client_id=" + spotifyProperties.getClientId() + "&" +
                "scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) + "&" +
                "redirect_uri=" + URLEncoder.encode(spotifyProperties.getRedirectUri(), StandardCharsets.UTF_8) + "&" +
                "state=" + state;

        response.sendRedirect(spotifyAuthUrl);

        return ResponseEntity.status(HttpStatus.FOUND).build();
    }




    @GetMapping("/callback")
    public ResponseEntity<?> handleRedirect(@RequestParam("code") String code) {
        System.out.println("Code: " + code);
        String tokenUrl = "https://accounts.spotify.com/api/token";

        // Crear el cuerpo de la solicitud
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", spotifyProperties.getRedirectUri());
        body.add("client_id", spotifyProperties.getClientId());
        body.add("client_secret", spotifyProperties.getClientSecret());

        // Crear el encabezado de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el token: " + e.getMessage());
        }
    }

}
