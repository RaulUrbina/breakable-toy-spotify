import useUserStore from "@/store/UserStore";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const Dashboard = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const setSessionId = useUserStore((state) => state.setSessionId);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const userId = params.get('userId');

    if (userId) {
        setSessionId(userId);

      params.delete('userId');
      navigate('/dashboard', { replace: true });
    }
  }, [location, navigate, setSessionId]);

  return <div>Login dachbord</div>; 
};

export default Dashboard;
