import TrackList from "@/components/custom/TrackList";
import { Skeleton } from "@/components/ui/skeleton";
import { fetchAlbumDetails } from "@/services/ApiService";
import { Album } from "@/types/Album";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const AlbumPage = () => {
  const { id } = useParams<{ id: string }>();
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [album, setAlbum] = useState<Album | null>(null);

  useEffect(() => {
    // Fetch data
    const loadAlbumDetails = async () => {
      try {
        // Fetch album details
        if (!id) return;
        const albumData = await fetchAlbumDetails(id);
        console.log("Album Details:", albumData);
        setAlbum(albumData);
      } catch (err) {
        console.log("Error fetching album details:", err);
      } finally {
        setIsLoading(false);
      }
    };

    loadAlbumDetails();
  }, [id]);

  const calculateTotalDuration = (tracks: Album["tracks"]): string => {
    const totalMs = tracks.reduce((acc, track) => acc + track.duration_ms, 0);
    const minutes = Math.floor(totalMs / 60000);
    const seconds = Math.floor((totalMs % 60000) / 1000);
    return `${minutes}m ${seconds}s`;
  };


  return (
    <div className="p-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
      {/* Left Column */}
      <div className="flex flex-col items-center">
        {/* Contenedor para imagen y Skeleton */}
        <div className="w-4/5 h-3/5 rounded mb-4 flex items-center justify-center bg-gray-200">
          {isLoading ? (
            <Skeleton className="w-full h-full rounded" />
          ) : (
            <img
              src={album?.images[0].url}
              alt={album?.name}
              className="w-full h-full object-cover rounded"
            />
          )}
        </div>
        {/* Contenedor para el nombre */}
        <div className="w-full h-8">
          {isLoading ? (
            <Skeleton className="w-full h-full rounded" />
          ) : (
            <h1 className="text-5xl font-bold text-gray-800 text-center">
              {album?.name}
            </h1>
          )}
        </div>
      </div>

      {/* Right Column */}
      <div className=" gap-4 h-full">
        {/* Top Songs */}
        <div className="rounded-md p-4 flex flex-col h-81 overflow-hidden">
          <h2 className="text-xl font-semibold mb-4 text-gray-800">
            Top Songs
          </h2>
          <div className="h-full overflow-y-auto">
            <TrackList
              tracks={album?.tracks.map((track) => ({
                ...track,
                smallImage: album?.images[0].url || "",
              }))}
              isLoading={isLoading}
            />
          </div>
        </div>

        <div className="bg-white shadow-md rounded-md p-4 flex flex-col justify-center items-start">
          {isLoading ? (
            <Skeleton className="h-4 w-3/4 mb-2" />
          ) : (
            <>
              <p className="text-gray-800 text-lg">
                <span className="font-semibold">Release Year:</span>{" "}
                {album?.release_date.split("-")[0]}
              </p>
              <p className="text-gray-800 text-lg mt-2">
                <span className="font-semibold">Total Duration:</span>{" "}
                {album?.tracks ? calculateTotalDuration(album.tracks) : "0m 0s"}
              </p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default AlbumPage;
