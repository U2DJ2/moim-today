// React
import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { GET } from "../../utils/axios";

// Icons
import HomeIcon from "@mui/icons-material/Home";
import ArticleIcon from "@mui/icons-material/Article";
import PersonIcon from "@mui/icons-material/Person";

// Components
import ProfileSection from "./ProfileSection";
import OverviewSection from "./OverviewSection";

// CSS
import "./Manage.css";

function SidebarElementIcon() {
  return (
    <div className="flex flex-col justify-center items-start p-5 bg-rose-600 rounded-3xl max-md:pr-5">
      <img
        loading="lazy"
        src="https://cdn.builder.io/api/v1/image/assets/TEMP/a62114f657cd2fa44c2617cafc5a564692ee84397f85414112c4d73292a6479e?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
        alt=""
        className="w-7 aspect-square"
      />
    </div>
  );
}

function SidebarElementLink({ icon, text, color, onClick }) {
  return (
    <div
      className={`flex items-center gap-2.5 py-4 text-base font-semibold ${color} cursor-pointer`}
      onClick={onClick}
    >
      {icon}
      <div className="flex-1 flex items-center justify-between">{text}</div>
    </div>
  );
}

// Sidebar 컴포넌트
function Sidebar({ activeSection, setActiveSection }) {
  const navigate = useNavigate();

  // 홈 버튼 클릭 시 메인 페이지로 이동
  const handleHome = () => {
    navigate("/");
  };

  return (
    <aside className="flex flex-col w-[30%] max-md:ml-0 max-md:w-full">
      <div className="flex flex-col grow items-start self-stretch py-4 pr-3 pl-16 max-md:pl-5 max-md:mt-6">
        <SidebarElementIcon />
        <div className="mt-3 text-base font-semibold text-center text-zinc-500">
          모임 대시보드{" "}
        </div>
        <div className="flex gap-2.5 mt-5"></div>
        <SidebarElementLink
          icon={<HomeIcon />}
          text="홈"
          color="text-gray-400"
          className="mt-16 max-md:mt-10"
          onClick={handleHome}
        />
        <SidebarElementLink
          icon={<ArticleIcon />}
          text="모임 관리"
          color={
            activeSection === "overview" ? "text-red-600" : "text-gray-400"
          }
          className="mt-16 max-md:mt-10"
          onClick={() => setActiveSection("overview")}
        />
        <SidebarElementLink
          icon={<PersonIcon />}
          text="프로필 설정"
          color={activeSection === "profile" ? "text-red-600" : "text-gray-400"}
          className="mt-2.5"
          onClick={() => setActiveSection("profile")}
        />
      </div>
    </aside>
  );
}

// Manage 컴포넌트
export default function Manage() {
  const location = useLocation();
  const [activeSection, setActiveSection] = useState("overview");
  const [name, setName] = useState("");
  const [major, setMajor] = useState("");

  useEffect(() => {
    GET("/api/members/profile")
      .then((res) => {
        console.log(res);
        setName(res.username);
        setMajor(res.departmentName);
        console.log(major);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    // 위치 정보에서 컴포넌트 이름을 가져와서 활성 섹션을 설정합니다.
    if (location.state?.componentName === "overview") {
      setActiveSection("overview");
    } else {
      setActiveSection("profile");
    }
  }, [location.state?.componentName]);

  return (
    <div className="justify-between pt-9 bg-white h-screen flex flex-col">
      <div className="flex-1 overflow-auto">
        <div className="flex gap-5 h-full w-full max-md:flex-col max-md:gap-0">
          <Sidebar
            activeSection={activeSection}
            setActiveSection={setActiveSection}
          />
          {activeSection === "overview" ? (
            <OverviewSection />
          ) : (
            <ProfileSection name={name} major={major} />
          )}
        </div>
      </div>
    </div>
  );
}
