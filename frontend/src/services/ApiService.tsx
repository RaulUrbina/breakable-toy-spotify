const API_BASE_URL = "http://localhost:8080";

import useUserStore from "@/store/UserStore";
import { Album } from "@/types/Album";
import { AlbumPreview } from "@/types/AlbumPreview";
import { ApiResponse, TopArtistsResponse } from "@/types/ApiResponse";
import { Artist } from "@/types/Artist";
import { ArtistPreview } from "@/types/ArtistPreview";
import { Track } from "@/types/Track";

export const fetchWithSession = async (endpoint: string, options?: RequestInit): Promise<Response> => {
  const sessionId = useUserStore.getState().sessionId;

  const headers = new Headers(options?.headers || {});
  if (sessionId) {
    headers.set("Session-Id", sessionId); 
  }

  return fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
    credentials: "include",
  });
};

export const fetchTopArtists = async (): Promise<ArtistPreview[]> => {
  const response = await fetchWithSession("/me/top/artists", {
    method: "GET",
  });

  if (!response.ok) {
    throw new Error("Failed to fetch top artists");
  }

  const data: TopArtistsResponse = await response.json();
  const artistList = data.data;
  return artistList;
};

export const fetchArtistTopSongs = async (id: string): Promise<Track[]> => {
  try {
    const response = await fetchWithSession(`/artists/${id}/top-tracks`, {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error("Failed to fetch artist top songs");
    }

    const jsonResponse: ApiResponse<Track[]> = await response.json();
    return jsonResponse.data;
  } catch (error) {
    console.error("Error fetching artist top songs:", error);
    throw error;
  }
}

export const fetchArtistDetails = async (id: string): Promise<Artist> => {
  try {
    const response = await fetchWithSession(`/artists/${id}`, {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error("Failed to fetch artist details");
    }

    const jsonResponse: ApiResponse<Artist> = await response.json();
    return jsonResponse.data;
  } catch (error) {
    console.error("Error fetching artist details:", error);
    throw error;
  }
};

export const fetchArtistAlbums = async (id: string): Promise<AlbumPreview[]> => {
  if (!id) {

    throw new Error("Artist ID is required");

  }
  try {
    const response = await fetchWithSession(`/artists/${id}/albums`, {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error("Failed to fetch artist albums");
    }

    const jsonResponse: ApiResponse<AlbumPreview[]> = await response.json();
    return jsonResponse.data;
  } catch (error) {
    console.error("Error fetching artist albums:", error);
    throw error;
  }
}

export const fetchAlbumDetails = async (id: string): Promise<Album> => {
  try {
    const response = await fetchWithSession(`/albums/${id}`, {
      method: "GET",
    });

    if (!response.ok) {
      throw new Error("Failed to fetch album details");
    }

    const jsonResponse: ApiResponse<Album> = await response.json();
    return jsonResponse.data;
  } catch (error) {
    console.error("Error fetching album details:", error);
    throw error;
  }
}

interface SearchResult {
  artists: ArtistPreview[];
  albums: AlbumPreview[];
  tracks: Track[];
}

export const search = async (query: string): Promise<SearchResult> => {
  try {
    console.log("Search query:", query);
    const response = await fetchWithSession(`/search?q=${encodeURIComponent(query)}`);
    console.log("Search response:", response);
    const data: ApiResponse<{
      artists: { items: ArtistPreview[] };
      albums: { items: AlbumPreview[] };
      tracks: { items: Track[] };
    }> = await response.json();

    if (!data.success) {
      throw new Error(data.message || "Search failed.");
    }

    const transformedTracks = data.data.tracks.items.map((track) => ({
      ...track,
      smallImage: track.album.smallImage,
      mediumImage: track.album.mediumImage,
      largeImage: track.album.largeImage,
    }));

    return {
      artists: data.data.artists.items,
      albums: data.data.albums.items,
      tracks: transformedTracks,
    };

  } catch (error) {
    console.error("Error performing search:", error);
    throw error;
  }
};


export const redirectToSpotifyAuth = async (): Promise<void> => {
  window.location.href = `${API_BASE_URL}/auth/spotify`;
}