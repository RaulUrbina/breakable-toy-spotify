import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import useUserStore from "@/store/UserStore";
import { UserIcon } from "lucide-react";
import { Link } from "react-router-dom";

const Header = () => {
  const clearSessionId = useUserStore((state) => state.clearSessionId);
  const handleLogout = () => {
    clearSessionId();
  };

  return (
    <header className="flex justify-between items-center p-4 bg-gray-900 text-white shadow-md">
      <div className="flex-1"></div>

      <Link
        to="/"
        className="text-2xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-[#3BAFDA] via-[#F4B400] to-[#EA4335] bg-[length:200%_200%] animate-text-gradient hover:opacity-80 transition"
      >
        Sparkify
      </Link>

      <div className="flex-1 flex justify-end">
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <button className="p-2 rounded-full bg-gray-700 hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500">
              <UserIcon className="w-6 h-6" />
            </button>
          </DropdownMenuTrigger>
          <DropdownMenuContent className="bg-white text-gray-900">
            <DropdownMenuItem onClick={handleLogout}>Logout</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
  );
};

export default Header;
