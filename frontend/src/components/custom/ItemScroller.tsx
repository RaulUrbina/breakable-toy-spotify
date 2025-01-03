import { useEffect, useState } from "react";
import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area";
import ItemCard from "./ItemCard";

interface ItemScrollerProps<T> {
  fetchItems: (id?: string) => Promise<T[]>;
  type: "artist" | "album" | "track";
  maxItems?: number;
}

interface Item {
  id: string;
  name: string;
  mediumImage: string;
}

const ItemScroller = <T extends Item>({
  fetchItems,
  type,
  maxItems = 10,
}: ItemScrollerProps<T>) => {
  const [items, setItems] = useState<T[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const loadItems = async () => {
      try {
        const data = await fetchItems();
        setItems(data);
      } catch (err) {
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    loadItems();
  }, [fetchItems, isLoading]);

  return (
    <ScrollArea className="w-full rounded-md border">
      <div className="flex w-max space-x-4 p-4 bg-slate-200">
        {isLoading
          ? Array.from({ length: maxItems }).map((_, index) => (
              <div key={index} className="shrink-0">
                <ItemCard id="" name="" image="" type={type} isLoading={true} />
              </div>
            ))
          : items.map((item) => (
              <div key={item.id} className="shrink-0">
                <ItemCard
                  id={item.id}
                  name={item.name}
                  image={item.mediumImage}
                  type={type}
                />
              </div>
            ))}
      </div>
      <ScrollBar orientation="horizontal" />
    </ScrollArea>
  );
};

export default ItemScroller;
