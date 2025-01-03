package com.spark.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpotifyEndpoints {
    public static final String AUTHORIZE_URL = "https://accounts.spotify.com/authorize";
    public static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    public static final String BASE_URL = "https://api.spotify.com/v1";
    public static final String ME = BASE_URL + "/me";
    public static final String TOP_ARTISTS = ME + "/top/artists";
    public static final String ARTIST_DETAILS = BASE_URL + "/artists/{id}";
    public static final String ARTIST_ALBUMS = ARTIST_DETAILS + "/albums";
    public static final String ALBUM_DETAILS = BASE_URL + "/albums/{id}";
    public static final String ARTIST_TOP_TRACKS = ARTIST_DETAILS + "/top-tracks";
    public static final String SEARCH = BASE_URL + "/search";

}
