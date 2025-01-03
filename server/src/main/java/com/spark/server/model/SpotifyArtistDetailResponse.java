package com.spark.server.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SpotifyArtistDetailResponse {
    private String id;
    private String name;
    private List<String> genres;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("images")
    private List<SpotifyImage> images;

    private String spotifyUrl;

    private int followersNumber;

    @JsonSetter("external_urls")
    public void setSpotifyUrl(Map<String, String> externalUrls) {
        if (externalUrls != null) {
            this.spotifyUrl = externalUrls.get("spotify");
        }
    }

    @JsonSetter("followers")
    public void setFollowers(Map<String, Object> followers) {
        if (followers != null && followers.containsKey("total")) {
            this.followersNumber = ((Number) followers.get("total")).intValue();
        }
    }
}
