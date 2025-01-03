package com.spark.server.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyArtist {
    private String id;
    private String name;
    private List<SpotifyImage> images;
}
