import { Track } from "./Track";

export interface Album {
    id: string;
    name: string;
    label: string;
    genres: string[];
    release_date: string;
    total_tracks: number;
    popularity: number;
    external_urls: string;
    images: AlbumImage[];
    artists: AlbumArtist[];
    tracks: Track[];
  }
  
  export interface AlbumImage {
    url: string;
    height: number;
    width: number;
  }
  
  export interface AlbumArtist {
    id: string;
    name: string;
  }
  
  export interface AlbumTrack {
    id: string;
    name: string;
    artists: AlbumArtist[];
    duration_ms: number;
  }
  