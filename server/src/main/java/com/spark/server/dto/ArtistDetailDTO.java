package com.spark.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class ArtistDetailDTO extends ArtistDTO{
    private List<String> genres;
    private String spotifyUrl;
    private int followersNumber;
    private int popularity;
}
