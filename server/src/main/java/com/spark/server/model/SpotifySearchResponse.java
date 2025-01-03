package com.spark.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchResponse {
    @JsonProperty("artists")
    private Artists artists;

    @JsonProperty("albums")
    private Albums albums;

    @JsonProperty("tracks")
    private Tracks tracks;

    @Data
    public static class Artists {
        private List<Artist> items;

        @Data
        public static class Artist {
            private String id;
            private String name;

            private String smallImage;
            private String mediumImage;
            private String largeImage;

            @JsonSetter("images")
            public void setImages(List<SpotifyImage> images) {
                if (images != null && !images.isEmpty()) {
                    this.largeImage = images.get(0).getUrl();
                    this.mediumImage = images.size() > 1 ? images.get(1).getUrl() : null;
                    this.smallImage = images.size() > 2 ? images.get(2).getUrl() : null;
                }
            }
        }
    }

    @Data
    public static class Albums {
        private List<Album> items;

        @Data
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
                    this.mediumImage = images.size() > 1 ? images.get(1).getUrl() : null;
                    this.smallImage = images.size() > 2 ? images.get(2).getUrl() : null;
                }
            }
        }
    }

    @Data
    public static class Tracks {
        private List<Track> items;

        @Data
        public static class Track {
            private String id;
            private String name;
            private String previewUrl;

            @JsonProperty("album")
            private Album album;

            @Data
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
                        this.mediumImage = images.size() > 1 ? images.get(1).getUrl() : null;
                        this.smallImage = images.size() > 2 ? images.get(2).getUrl() : null;
                    }
                }
            }
        }
    }
}
