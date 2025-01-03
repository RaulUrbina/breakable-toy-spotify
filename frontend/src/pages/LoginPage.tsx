import { redirectToSpotifyAuth } from "@/services/ApiService";
import { FaSpotify } from "react-icons/fa";
import useUserStore from "@store/UserStore";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect } from "react";

const LoginPage = () => {
  const sessionId = useUserStore((state) => state.sessionId);
  const navigate = useNavigate();
  const location = useLocation();
  const setSessionId = useUserStore((state) => state.setSessionId);

  useEffect(() => {
    if (sessionId) {
      navigate(0);
    }
    const params = new URLSearchParams(location.search);
    const userId = params.get("userId");
    if (userId) {
      setSessionId(userId);
      params.delete("userId");
      navigate(0);
    }
  }, [location, navigate, setSessionId, sessionId]);

  const handleLogin = () => {
    redirectToSpotifyAuth();
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-black">
      <h1 className="text-9xl font-bold text-transparent mb-12 drop-shadow-lg animate-text-gradient bg-clip-text bg-gradient-to-r from-[#3BAFDA] via-[#F4B400] to-[#EA4335] bg-[length:200%_200%]">
        Sparkify
      </h1>

      <button
        className="flex items-center bg-green-500 text-white py-3 px-6 rounded-full text-lg font-medium hover:bg-green-600 transition focus:outline-none focus:ring-4 focus:ring-green-300"
        onClick={() => {
          handleLogin();
        }}
      >
        <FaSpotify className="w-6 h-6 mr-3" />
        Log in with Spotify
      </button>
    </div>
  );
};

export default LoginPage;
