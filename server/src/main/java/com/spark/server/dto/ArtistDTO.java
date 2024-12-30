package com.spark.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDTO {
    private String id;
    private String name;
    private String smallImage;
    private String mediumImage;
    private String largeImage;
}
