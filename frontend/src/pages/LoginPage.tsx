import ApiService from "@/services/ApiService";
import { FaSpotify } from "react-icons/fa";
import useUserStore from "@store/UserStore";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const LoginPage = () => {
  const sessionId = useUserStore((state) => state.sessionId);
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionId) {
      navigate("/dashboard");
    }
  }, [sessionId, navigate]);

  const handleLogin = () => {
    ApiService.redirectToSpotifyAuth();
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-[#3BAFDA] via-[#F4B400] to-[#EA4335] bg-[length:200%_200%] animate-gradient">
      <h1 className="text-6xl font-bold text-white mb-12 drop-shadow-lg">
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
