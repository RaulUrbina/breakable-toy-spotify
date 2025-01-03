import { ArtistPreview } from "@/types/ArtistPreview";

export type TopArtistsResponse = ApiResponse<ArtistPreview[]>;


export interface ApiResponse<T> {
    success: boolean;
    message: string;
    data: T;
  }
  