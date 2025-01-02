const API_BASE_URL = "http://localhost:8080";

import useUserStore from "@/store/UserStore";
import { TopArtistsResponse } from "@/types/ApiResponse";
import { ArtistPreview } from "@/types/ArtistPreview";

export const fetchWithSession = async (endpoint: string, options?: RequestInit): Promise<Response> => {
  const sessionId = useUserStore.getState().sessionId;

  console.log("sessionId", sessionId);
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


  

class ApiService {

    async redirectToSpotifyAuth(): Promise<void> {
        window.location.href = `${API_BASE_URL}/auth/spotify`;
      }
    
  }

  export default new ApiService();
  