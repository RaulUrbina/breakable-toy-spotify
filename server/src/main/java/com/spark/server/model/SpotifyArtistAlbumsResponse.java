package com.spark.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyArtistAlbumsResponse {
    private int total;
    private int limit;
    private int offset;
    private String href;
    private String next;
    private String previous;

    @JsonProperty("items")
    private List<Album> albums;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Album {
        private String id;
        private String name;

        @JsonProperty("release_date")
        private String releaseDate;

        private String smallImage;
        private String mediumImage;
        private String largeImage;

        @JsonProperty("album_group")
        private String albumGroup;

        @JsonProperty("artists")
        private List<Artist> artists;

        @JsonSetter("images")
        public void setImages(List<Map<String, Object>> images) {
            if (images != null && !images.isEmpty()) {
                this.largeImage = (String) images.get(0).get("url");
                if (images.size() > 1) {
                    this.mediumImage = (String) images.get(1).get("url");
                }
                if (images.size() > 2) {
                    this.smallImage = (String) images.get(2).get("url");
                }
            }
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Artist {
            private String id;
            private String name;
        }
    }

}
