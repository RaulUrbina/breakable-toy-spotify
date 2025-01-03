import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  fetchArtistAlbums,
  fetchArtistDetails,
  fetchArtistTopSongs,
} from "@/services/ApiService";
import { Artist } from "@/types/Artist";
import { Track } from "@/types/Track";
import { Skeleton } from "@/components/ui/skeleton";
import TrackList from "@/components/custom/TrackList";
import ItemScroller from "@/components/custom/ItemScroller";
import { AlbumPreview } from "@/types/AlbumPreview";

const ArtistPage = () => {
  const { id } = useParams<{ id: string }>();
  const [artist, setArtist] = useState<Artist | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [isLoadingTopSongs, setIsLoadingTopSongs] = useState<boolean>(true);
  const [topSongs, setTopSongs] = useState<Track[]>([]);

  useEffect(() => {
    const loadArtistDetails = async () => {
      try {
        if (!id) return;
        const artistData = await fetchArtistDetails(id);
        console.log("Artist Details:", artistData);
        setArtist(artistData);
      } catch (err) {
        setError("Failed to fetch artist details: " + err);
      } finally {
        setIsLoading(false);
      }
    };

    const loadArtistTopSongs = async () => {
      try {
        if (!id) return;
        const songs = await fetchArtistTopSongs(id);
        setTopSongs(songs);
      } catch (err) {
        setError("Failed to fetch artist top songs: " + err);
      } finally {
        setIsLoadingTopSongs(false);
      }
    };

    loadArtistDetails();
    loadArtistTopSongs();
  }, [id]);

  if (error) {
    return <div className="text-red-500 p-4">{error}</div>;
  }

  return (
    <div className="p-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
      {/* Left Column */}
      <div className="flex flex-col items-center">
        <div className="w-4/5 h-3/5 rounded mb-4 flex items-center justify-center bg-gray-200">
          {isLoading ? (
            <Skeleton className="w-full h-full rounded" />
          ) : (
            <img
              src={artist?.largeImage}
              alt={artist?.name}
              className="w-full h-full object-cover rounded"
            />
          )}
        </div>
        <div className="w-1/2 h-8">
          {isLoading ? (
            <Skeleton className="w-full h-full rounded" />
          ) : (
            <h1 className="text-5xl font-bold text-gray-800 text-center">
              {artist?.name}
            </h1>
          )}
        </div>
      </div>

      {/* Right Column */}
      <div className="grid grid-rows-2 gap-4 h-full">
        {/* Top Songs */}
        <div className="rounded-md p-4 flex flex-col h-81 overflow-hidden">
          <h2 className="text-xl font-semibold mb-4 text-gray-800">
            Top Songs
          </h2>
          <div className="h-full overflow-y-auto">
            <TrackList tracks={topSongs} isLoading={isLoadingTopSongs} />
          </div>
        </div>


        <div className="bg-white rounded-md p-4 flex flex-col h-auto overflow-y-auto ">
          <h2 className="text-xl font-semibold mb-4 text-gray-800">Albums</h2>
          <div className="h-full overflow-y-auto">
          <ItemScroller<AlbumPreview>
            fetchItems={() => {
              if (!id) {
                return Promise.resolve([]);
              }
              return fetchArtistAlbums(id);
            }}
            type="album"
            maxItems={10}
          />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ArtistPage;
