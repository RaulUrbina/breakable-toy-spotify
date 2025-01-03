package com.spark.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumPreviewDTO {
    private String id;
    private String name;
    private String smallImage;
    private String mediumImage;
    private String largeImage;
    private String artistId;
    private String artistName;
}
