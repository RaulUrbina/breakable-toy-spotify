package com.spark.server.token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class TokenData {
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;
}
