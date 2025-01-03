package com.spark.server.dto;

import lombok.Data;

@Data
public class TrackDTO {
    private String id;
    private String name;
    private String albumId;
    private String albumName;
    private String smallImage;
    private String mediumImage;
    private String largeImage;
    private Long duration;
    private String spotifyUrl;
}
