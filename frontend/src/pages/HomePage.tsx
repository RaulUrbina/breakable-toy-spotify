import ItemScroller from "@/components/custom/ItemScroller";
import SearchComponent from "@/components/custom/SearchComponent";
import { fetchTopArtists } from "@/services/ApiService";
import { ArtistPreview } from "@/types/ArtistPreview";

const HomePage = () => {

  return (
    <div className="p-8 space-y-12">
    {/* Top Artists Section */}
    <div className="space-y-4">
      <h2 className="text-2xl font-bold text-gray-800">Top Artists</h2>
      <ItemScroller<ArtistPreview>
        fetchItems={fetchTopArtists}
        type="artist"
        maxItems={10}
      />
    </div>

    {/* Search Section */}
    <div className="space-y-4">
      <h2 className="text-2xl font-bold text-gray-800">Search</h2>
      <SearchComponent />
    </div>
  </div>
  );

};

export default HomePage;
