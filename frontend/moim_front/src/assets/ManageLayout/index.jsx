// React
import { Outlet } from "react-router";

// Components
import Sidebar from "./SidebarElement";

function ManageLayout() {
  return (
    <div className="justify-between pt-6 bg-white h-screen flex flex-col">
      <div className="flex-1 overflow-auto">
        <div className="flex gap-5 min-h-screen w-full max-md:flex-col max-md:gap-0">
          <Sidebar />
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default ManageLayout;
