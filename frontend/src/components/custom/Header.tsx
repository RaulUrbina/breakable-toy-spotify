import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { UserIcon } from "lucide-react";

const Header = () => {
  const user = {
    name: "John Doe",
    email: "john.doe@example.com",
  };

  const handleRedirect = () => {
    console.log("Redirect clicked!");
  };

  const handleLogout = () => {
    console.log("Logout clicked!");
  };

  return (
    <header className="flex justify-between items-center p-4 bg-gray-900 text-white shadow-md">
      <div className="flex-1"></div>

      <h1 className="text-2xl font-bold flex-1 text-center">Sparkify</h1>

      <div className="flex-1 flex justify-end">
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <button className="p-2 rounded-full bg-gray-700 hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500">
              <UserIcon className="w-6 h-6" />
            </button>
          </DropdownMenuTrigger>
          <DropdownMenuContent className="bg-white text-gray-900">
            <DropdownMenuItem>
              <span className="font-semibold">{user.name}</span>
            </DropdownMenuItem>
            <DropdownMenuItem>
              <span className="text-sm">{user.email}</span>
            </DropdownMenuItem>
            <DropdownMenuItem onClick={handleRedirect}>
              Redirect
            </DropdownMenuItem>
            <DropdownMenuItem onClick={handleLogout}>Logout</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
  );
};

export default Header;
