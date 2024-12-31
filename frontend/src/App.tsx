import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "@/features/Login/index.tsx";
import Dashboard from "@/features/Dashboard";
import useUserStore from "@store/UserStore";

function App() {
  const RootRedirect = () => {
    const sessionId = useUserStore((state) => state.sessionId); 
    return sessionId ? <Navigate to="/dashboard" /> : <Navigate to="/login" />;
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<RootRedirect />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
