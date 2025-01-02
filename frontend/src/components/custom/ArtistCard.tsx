import React from "react";
import { ArtistPreview } from "@/types/ArtistPreview";
import { Skeleton } from "@/components/ui/skeleton";

interface ArtistCardProps {
  artist?: ArtistPreview;
  isLoading?: boolean;
}

const ArtistCard: React.FC<ArtistCardProps> = ({
  artist,
  isLoading = false,
}) => {
  return (
    <div className="flex flex-col items-center bg-white shadow-md rounded-lg p-4 w-48 h-54">
      {isLoading ? (
        <Skeleton className="w-32 h-32 rounded-full mb-4" />
      ) : (
        <img
          src={artist?.mediumImage}
          alt={artist?.name}
          className="w-32 h-32 object-cover rounded-full mb-4"
        />
      )}

      <div className="h-6 w-full flex items-center justify-center">
        {isLoading ? (
          <Skeleton className="h-4 w-3/4" />
        ) : (
          <h2 className="text-lg font-semibold text-gray-800 text-center truncate">
            {artist?.name}
          </h2>
        )}
      </div>
    </div>
  );
};

export default ArtistCard;
