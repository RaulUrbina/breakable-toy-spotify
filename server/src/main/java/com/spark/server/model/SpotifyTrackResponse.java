package com.spark.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackResponse {
    private List<Track> tracks;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Track {
        private String id;
        private String name;
        private List<Artist> artists;

        @JsonProperty("album")
        private Album album;

        @JsonProperty("duration_ms")
        private Long durationMs;

        @JsonProperty("popularity")
        private int popularity;

        @JsonProperty("preview_url")
        private String previewUrl;

        private String spotifyUrl;

        @JsonSetter("external_urls")
        public void setSpotifyUrl(Map<String, String> externalUrls) {
            if (externalUrls != null) {
                this.spotifyUrl = externalUrls.get("spotify");
            }
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Artist {
            private String id;
            private String name;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Album {
            private String id;
            private String name;

            private String smallImage;
            private String mediumImage;
            private String largeImage;

            @JsonSetter("images")
            public void setImages(List<SpotifyImage> images) {
                if (images != null && !images.isEmpty()) {
                    this.largeImage = images.get(0).getUrl();
                    this.mediumImage = images.get(1).getUrl();
                    this.smallImage = images.get(2).getUrl();
                }
            }
        }
    }
}
