import React, { useEffect } from "react";
import Header from "@/components/custom/Header";
import { useLocation, useNavigate } from "react-router-dom";
import useUserStore from "@/store/UserStore";

const MainLayout = ({ children }: { children: React.ReactNode }) => {

  const navigate = useNavigate();
  const location = useLocation();
  const sessionId = useUserStore((state) => state.sessionId);
  const setSessionId = useUserStore((state) => state.setSessionId);
  

  useEffect(() => {

    console.log("MainLayout useEffect");
    const params = new URLSearchParams(location.search);
    const userId = params.get("userId");
    if (userId) {
      setSessionId(userId);

      params.delete("userId");
      navigate("/", { replace: true });
    } else if (!sessionId) {
      navigate("/");
    }
    
  }, [location, navigate, setSessionId, sessionId]);
  
  return (
    <div>
      <Header />
      <main className="p-4">{children}</main>
    </div>
  );
};

export default MainLayout;
