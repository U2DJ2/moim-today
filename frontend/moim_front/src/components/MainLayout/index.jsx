import Header from "../Header";
import { Outlet } from "react-router";

function MainLayout() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default MainLayout;
