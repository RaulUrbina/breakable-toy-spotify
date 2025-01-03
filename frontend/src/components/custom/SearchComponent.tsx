import { useState } from "react";
import ItemScroller from "@/components/custom/ItemScroller";
import { search } from "@/services/ApiService";
import { ArtistPreview } from "@/types/ArtistPreview";
import { AlbumPreview } from "@/types/AlbumPreview";
import { Track } from "@/types/Track";
import { Skeleton } from "@/components/ui/skeleton";
import { CiSearch } from "react-icons/ci";

const SearchComponent = () => {
  const [query, setQuery] = useState<string>("");
  const [artists, setArtists] = useState<ArtistPreview[]>([]);
  const [albums, setAlbums] = useState<AlbumPreview[]>([]);
  const [tracks, setTracks] = useState<Track[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [hasSearched, setHasSearched] = useState<boolean>(false);

  const handleSearch = async () => {
    if (!query.trim()) return;

    setIsLoading(true);
    setHasSearched(true);
    try {
      const results = await search(query);
      setArtists(results.artists);
      setAlbums(results.albums);
      setTracks(results.tracks);
    } catch (error) {
      console.error("Search failed:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="p-6">
      {/* Search Bar */}
      <div className="flex items-center space-x-4 mb-6">
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search for artists, albums, or tracks..."
          className="flex-1 px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-blue-300"
        />
        <button
          onClick={handleSearch}
          className="px-4 py-2 bg-gray-700 text-white rounded-md hover:bg-blue-600 transition"
        >
          <CiSearch />
        </button>
      </div>

      {/* Results */}
      {hasSearched && (
        <div className="space-y-8">
          {/* Artists */}
          <div>
            <h2 className="text-xl font-semibold mb-4">Artists</h2>
            {isLoading ? (
              <Skeleton className="h-48 w-full rounded-md" />
            ) : (
              <ItemScroller<ArtistPreview>
                fetchItems={() => Promise.resolve(artists)}
                type="artist"
                maxItems={10}
              />
            )}
          </div>

          {/* Albums */}
          <div>
            <h2 className="text-xl font-semibold mb-4">Albums</h2>
            {isLoading ? (
              <Skeleton className="h-48 w-full rounded-md" />
            ) : (
              <ItemScroller<AlbumPreview>
                fetchItems={() => Promise.resolve(albums)}
                type="album"
                maxItems={10}
              />
            )}
          </div>

          {/* Tracks */}
          <div>
            <h2 className="text-xl font-semibold mb-4">Tracks</h2>
            {isLoading ? (
              <Skeleton className="h-48 w-full rounded-md" />
            ) : (
              <ItemScroller<Track>
                fetchItems={() => Promise.resolve(tracks)}
                type="track"
                maxItems={10}
              />
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default SearchComponent;
