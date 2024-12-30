package com.spark.server.map;

import com.spark.server.dto.ArtistDTO;
import com.spark.server.model.SpotifyArtist;
import com.spark.server.model.SpotifyImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ArtistMapper {
    public List<ArtistDTO> mapToArtistDTOs(List<SpotifyArtist> spotifyArtists) {
        return spotifyArtists.stream().map(this::mapToArtistDTO).collect(Collectors.toList());
    }

    private ArtistDTO mapToArtistDTO(SpotifyArtist spotifyArtist) {
        String smallImage = null;
        String mediumImage = null;
        String largeImage = null;

        List<SpotifyImage> images = spotifyArtist.getImages();
        if (images != null && !images.isEmpty()) {
            largeImage = images.get(0).getUrl();
            mediumImage = images.size() > 1 ? images.get(1).getUrl() : null;
            smallImage = images.size() > 2 ? images.get(2).getUrl() : null;
        }


        return new ArtistDTO(
                spotifyArtist.getId(),
                spotifyArtist.getName(),
                smallImage,
                mediumImage,
                largeImage
        );
    }


}
