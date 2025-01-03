package com.spark.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ArtistDTO {
    private String id;
    private String name;
    private String smallImage;
    private String mediumImage;
    private String largeImage;
}
