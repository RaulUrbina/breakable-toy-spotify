export interface Track {
    id: string;
    name: string;
    previewUrl: string | null;
    album: {
      id: string;
      name: string;
      smallImage: string;
      mediumImage: string;
      largeImage: string;
    };
    smallImage: string;
    mediumImage: string;
    largeImage: string;
  }
  