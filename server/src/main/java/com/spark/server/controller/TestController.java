package com.spark.server.controller;

import com.spark.server.dto.ApiResponse;
import com.spark.server.dto.ArtistDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
