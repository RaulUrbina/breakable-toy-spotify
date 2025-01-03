package com.spark.server.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyArtistResponse {
    private List<SpotifyArtist> items;
    private int total;
    private int limit;
    private int offset;
    private String href;
    private String next;
    private String previous;

}
