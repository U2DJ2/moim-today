import { useState, useEffect, useMemo } from "react";
import SidebarElementIcon from "./Icon";
import SidebarElementLink from "./navigation";
import { useLocation, useNavigate } from "react-router";

import HomeIcon from "@mui/icons-material/Home";
import ArticleIcon from "@mui/icons-material/Article";
import PersonIcon from "@mui/icons-material/Person";

function Sidebar() {
  const location = useLocation();
  const navigate = useNavigate();

  const sections = ["홈", "프로필 설정", "모임 관리"];

  const links = [<HomeIcon />, <PersonIcon />, <ArticleIcon />];

  const onClickHandler = (section) => {
    if (section === "홈") navigate("/");
    else if (section === "프로필 설정") {
      navigate("/manage");
    } else navigate("/manage/moim");
  };

  const currentSection = useMemo(() => {
    if (location.pathname === "/manage") {
      return "프로필 설정";
    } else {
      return "모임 관리";
    }
  }, [location.pathname]);

  return (
    <aside className="flex  flex-col w-[30%] max-md:ml-0 max-md:w-full">
      <div className="flex flex-col grow items-start self-stretch py-4 pr-3 pl-16 max-md:pl-5 max-md:mt-6">
        <SidebarElementIcon />
        <div className="mt-3 text-3xl pb-3 font-Pretendard_Black font-semibold text-center text-zinc-500">
          대시보드
        </div>
        <div>
          {sections.map((section, index) => {
            return (
              <SidebarElementLink
                key={index}
                icon={links[index]}
                text={section}
                className="mt-16 max-md:mt-10"
                color={
                  currentSection === section ? "text-scarlet" : "text-gray-400"
                }
                onClick={() => onClickHandler(section)}
              />
            );
          })}
        </div>
      </div>
    </aside>
  );
}

export default Sidebar;
