const BASE_URL = "http://localhost:8080";

class ApiService {

    async redirectToSpotifyAuth(): Promise<void> {
        window.location.href = `${BASE_URL}/auth/spotify`;
      }
    
  }

  export default new ApiService();
  