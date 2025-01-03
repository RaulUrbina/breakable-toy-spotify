package com.spark.server.map;

import com.spark.server.dto.AlbumPreviewDTO;
import com.spark.server.dto.TrackDTO;
import com.spark.server.model.SpotifySearchResponse;
import com.spark.server.model.SpotifyTrackResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrackMapper {
    public List<TrackDTO> mapToTrackDTOs(List<SpotifyTrackResponse.Track> tracks) {
        if (tracks == null || tracks.isEmpty()) {
            return List.of();
        }

        return tracks.stream()
                .map(this::mapToTrackDTO)
                .collect(Collectors.toList());
    }

    public TrackDTO mapToTrackDTO(SpotifyTrackResponse.Track track) {
        if (track == null) {
            return null;
        }

        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(track.getId());
        trackDTO.setName(track.getName());

        if (track.getAlbum() != null) {
            trackDTO.setAlbumId(track.getAlbum().getId());
            trackDTO.setAlbumName(track.getAlbum().getName());
            trackDTO.setSmallImage(track.getAlbum().getSmallImage());
            trackDTO.setMediumImage(track.getAlbum().getMediumImage());
            trackDTO.setLargeImage(track.getAlbum().getLargeImage());
        }

        trackDTO.setDuration(track.getDurationMs());
        trackDTO.setSpotifyUrl(track.getSpotifyUrl());

        return trackDTO;
    }



}
