import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import HomePage from "./pages/HomePage";
import MainLayout from "@/components/custom/MainLayout";
import useUserStore from "@store/UserStore";

function App() {
  const sessionId = useUserStore((state) => state.sessionId); 
  const RootRedirect = () => {
    return sessionId ? <Navigate to="/dashboard" /> : <Navigate to="/login" />;
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<RootRedirect />} />
        <Route path="/login" element={<LoginPage />} />

        { (
          <>
            <Route
              path="/dashboard"
              element={
                <MainLayout>
                  <HomePage />
                </MainLayout>
              }
            />
            <Route
              path="/profile"
              element={
                <MainLayout>
                  <HomePage />
                </MainLayout>
              }
            />
          </>
        )}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
