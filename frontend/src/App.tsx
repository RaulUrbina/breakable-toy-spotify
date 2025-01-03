import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import HomePage from "./pages/HomePage";
import MainLayout from "@/components/custom/MainLayout";
import useUserStore from "@store/UserStore";
import ArtistPage from "@/pages/ArtistPage";
import AlbumPage from "@/pages/AlbumPage";
function App() {
  const sessionId = useUserStore((state) => state.sessionId);

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            sessionId ? (
              <MainLayout>
                <HomePage />
              </MainLayout>
            ) : (
              <LoginPage />
            )
          }
        />
        <Route
          path="/artists/:id"
          element={
            <MainLayout>
              <ArtistPage />
            </MainLayout>
          }
        />

        <Route
          path="/albums/:id"
          element={
            <MainLayout>
              <AlbumPage />
            </MainLayout>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
