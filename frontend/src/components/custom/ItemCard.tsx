import React from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { useNavigate } from "react-router-dom";

interface ItemCardProps {
  id: string;
  name: string;
  image: string;
  type: "artist" | "album" | "track";
  isLoading?: boolean;
}

const ItemCard: React.FC<ItemCardProps> = ({
  id,
  name,
  image,
  type,
  isLoading = false,
}) => {
  const navigate = useNavigate();

  const handleClick = () => {
    if (id) {
      navigate(`/${type}s/${id}`);
    }
  };

  return (
    <div
      onClick={handleClick}
      className="flex flex-col items-center bg-white shadow-md rounded-lg p-4 w-48 h-56 cursor-pointer hover:shadow-lg transition-shadow"
    >
      {isLoading ? (
        <Skeleton className="w-32 h-32 rounded-full mb-4" />
      ) : (
        <img
          src={image}
          alt={name}
          className="w-32 h-32 object-cover rounded-full mb-4"
        />
      )}

      <div className="h-6 w-full flex items-center justify-center">
        {isLoading ? (
          <Skeleton className="h-4 w-3/4" />
        ) : (
          <h2 className="text-lg font-semibold text-gray-800 text-center truncate">
            {name}
          </h2>
        )}
      </div>

      <div className="text-sm text-gray-600">{type === "artist" ? "Artist" : type === "album" ? "Album" : "Track"}</div>
    </div>
  );
};

export default ItemCard;
