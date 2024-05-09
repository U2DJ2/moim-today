// React
import { useNavigate } from 'react-router-dom';

import ProfileDropdown from '../Dropdown/Profile';

function Header() {
  const navigate = useNavigate();

  const handleManage = () => {
    navigate('/manage', {
      state: {
        componentName: "overview",
      },
    });
  };

  const handleSchedule = () => {
    navigate('/schedule', {
      state: {
        componentName: "overview",
      },
    });
  };

  return (
    <header className="flex gap-5 justify-between px-10 py-6 w-full text-lg leading-7 text-center text-gray-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
      <nav className="flex gap-5 justify-center my-auto font-Pretendard_SemiBold cursor-pointer">
        <p onClick={handleManage}>
          대시보드
        </p>
        <a href="/creation">모임 생성</a>
      </nav>
      <div className="flex gap-5 justify-center font-Pretendard_SemiBold whitespace-nowrap cursor-pointer">
        <div className="justify-center my-auto" onClick={handleSchedule}>아주대학교</div>
        <ProfileDropdown />
      </div>
    </header>
  );
}
export default Header;
