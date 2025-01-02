import React, { useEffect, useState } from "react";
import { fetchTopArtists } from "@/services/ApiService";
import { ArtistPreview } from "@/types/ArtistPreview";
import ArtistCard from "@/components/custom/ArtistCard";
import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area";

const MAX_ARTISTS = 10;

const TopArtists = () => {
  const [artists, setArtists] = useState<ArtistPreview[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const loadTopArtists = async () => {
      try {
        const artistsData = await fetchTopArtists();
        setArtists(artistsData);
      } catch (err) {
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    loadTopArtists();
  }, []);

  return (
    <ScrollArea className="w-full rounded-md border">
      <div className="flex w-max space-x-4 p-4">
        {isLoading
          ? Array.from({ length: MAX_ARTISTS }).map((_, index) => (
              <div key={index} className="shrink-0">
                <ArtistCard isLoading={true} />
              </div>
            ))
          : artists.map((artist) => (
              <div key={artist.id} className="shrink-0">
                <ArtistCard artist={artist} />
              </div>
            ))}
      </div>
      <ScrollBar orientation="horizontal" />
    </ScrollArea>
  );
};

export default TopArtists;
