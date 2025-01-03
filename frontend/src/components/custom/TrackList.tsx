import React from "react";
import { ScrollArea } from "@/components/ui/scroll-area";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Track } from "@/types/Track";
import { Skeleton } from "../ui/skeleton";

const formatDuration = (durationMs: number): string => {
  const minutes = Math.floor(durationMs / 60000);
  const seconds = Math.floor((durationMs % 60000) / 1000);
  return `${minutes}:${seconds.toString().padStart(2, "0")}`;
};

interface TrackListProps {
    tracks?: Track[];
    isLoading: boolean;
  }
  

const TrackList: React.FC<TrackListProps> = ({ tracks = [], isLoading }) => {
  return (
    <div className="bg-white rounded-md overflow-hidden">
      <ScrollArea className="h-96">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-16">Image</TableHead>
              <TableHead>Song Name</TableHead>
              <TableHead className="text-right">Duration</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {isLoading
              ? Array.from({ length: 5 }).map((_, index) => (
                  <TableRow key={index}>
                    <TableCell>
                      <Skeleton className="w-12 h-12 rounded" />
                    </TableCell>
                    <TableCell>
                      <Skeleton className="h-4 w-3/4" />
                    </TableCell>
                    <TableCell className="text-right">
                      <Skeleton className="h-4 w-1/4" />
                    </TableCell>
                  </TableRow>
                ))
              : tracks.map((track) => (
                  <TableRow key={track.id}>
                    {/* Album Image */}
                    <TableCell>
                      <img
                        src={track.smallImage}
                        alt={track.name}
                        className="w-12 h-12 object-cover rounded"
                      />
                    </TableCell>
                    {/* Song Name */}
                    <TableCell>{track.name}</TableCell>
                    {/* Duration */}
                    <TableCell className="text-right">{formatDuration(track.duration_ms || track.duration)}</TableCell>
                  </TableRow>
                ))}
          </TableBody>
        </Table>
      </ScrollArea>
    </div>
  );
};

export default TrackList;
