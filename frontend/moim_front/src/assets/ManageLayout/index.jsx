import { useState } from "react";
import Sidebar from "./SidebarElement";
import { Outlet } from "react-router";

function ManageLayout() {
  return (
    <div className="justify-between pt-6 bg-white h-screen flex flex-col">
      <div className="flex-1 overflow-auto">
        <div className="flex gap-5 h-full w-full max-md:flex-col max-md:gap-0">
          <Sidebar />
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default ManageLayout;
