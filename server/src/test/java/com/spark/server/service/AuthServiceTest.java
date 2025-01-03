package com.spark.server.service;

import com.spark.server.model.SpotifyTokenResponse;
import com.spark.server.token.TokenStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private SpotifyApiService spotifyApiService;

    @Mock
    private TokenStore tokenStore;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientTokensSuccess() {
        SpotifyTokenResponse mockResponse = new SpotifyTokenResponse();
        mockResponse.setAccessToken("test-access-token");
        mockResponse.setRefreshToken("test-refresh-token");
        mockResponse.setExpiresIn(3600L);

        when(spotifyApiService.getClientTokens()).thenReturn(mockResponse);

        authService.getClientTokens();

        verify(tokenStore).storeTokens(anyString(), eq("test-access-token"), eq("test-refresh-token"), eq(3600L));
        verify(spotifyApiService, times(1)).getClientTokens();
    }

    @Test
    void testGetClientTokensFailure() {
        when(spotifyApiService.getClientTokens()).thenReturn(null);

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            authService.getClientTokens();
        });

        org.junit.jupiter.api.Assertions.assertEquals("No access token found in the response.", exception.getMessage());

        
        verify(tokenStore, never()).storeTokens(anyString(), anyString(), anyString(), anyLong());
    }
}
