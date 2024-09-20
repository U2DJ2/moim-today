// React
import { useNavigate } from "react-router-dom";

import ProfileDropdown from "../Dropdown/Profile";

import { useEffect, useState } from "react";

import { GET } from "../../utils/axios";

function Header() {
  const [profileImg, setProfileImg] = useState("");
  const [userName, setUserName] = useState("");
  const navigate = useNavigate();

  const handleManage = () => {
    navigate("/manage", {
      state: {
        componentName: "overview",
      },
    });
  };

  const handleSchedule = () => {
    navigate("/schedule", {
      state: {
        componentName: "overview",
      },
    });
  };

  const getProfile = async () => {
    try {
      const result = await GET("api/members/profile");
      setUserName(result.username);
      setProfileImg(result.memberProfileImageUrl);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getProfile();
  }, []);

  return (
    <header className="flex gap-5 justify-between px-10 py-6 w-full text-lg leading-7 text-center text-gray-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
      <nav className="flex gap-5 justify-center my-auto font-Pretendard_SemiBold cursor-pointer">
        <p onClick={handleManage}>대시보드</p>
        <a href="/creation">모임 생성</a>
      </nav>
      <div className="flex gap-5 justify-center font-Pretendard_SemiBold whitespace-nowrap cursor-pointer">
        <div className="justify-center my-auto" onClick={handleSchedule}>
          캘린더
        </div>
        <div className="flex flex-col items-center">
          <div className="text-xs">{userName}</div>
          <ProfileDropdown image={profileImg} />
        </div>
      </div>
    </header>
  );
}
export default Header;
