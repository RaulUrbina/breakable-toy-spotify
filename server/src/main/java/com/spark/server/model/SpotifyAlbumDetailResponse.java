package com.spark.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumDetailResponse {
    private String id;

    private String name;

    @JsonProperty("release_date")
    private String releaseDate;


    @JsonProperty("total_tracks")
    private int totalTracks;

    private String spotifyUrl;

    @JsonProperty("images")
    private List<SpotifyImage> images;

    @JsonProperty("artists")
    private List<ArtistOverview> artists;

    private List<TrackOverview> tracks;


    @JsonSetter("external_urls")
    public void setSpotifyUrl(Map<String, String> externalUrls) {
        if (externalUrls != null) {
            this.spotifyUrl = externalUrls.get("spotify");
        }
    }

    @JsonSetter("tracks")
    public void setTracks(Map<String, Object> tracks) {
        if (tracks != null && tracks.containsKey("items")) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.tracks = objectMapper.convertValue(
                    tracks.get("items"),
                    new TypeReference<>() {
                    }
            );
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArtistOverview {
        private String id;
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackOverview {
        private String id;
        private String name;
        private List<ArtistOverview> artists;
        @JsonProperty("duration_ms")
        private int durationMs;
    }


}
