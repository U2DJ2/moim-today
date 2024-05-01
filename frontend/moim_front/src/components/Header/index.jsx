// React
import { useState } from "react";

// Components
import ModalTest from "../Modal/ModalTest";

function Header() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <header className="flex gap-5 font-Pretendard_Bold text-[#6B7280] justify-between px-10 py-6 w-full text-lg leading-7 text-center text-gray-500 max-md:flex-wrap max-md:px-5 max-md:max-w-full">
      <nav className="flex gap-5 justify-center my-auto font-semibold">
        <a href="#" onClick={openModal}>
          대시보드
        </a>
        <ModalTest isOpen={isModalOpen} closeModal={closeModal} />
        <a href="/creation">모임 생성</a>
      </nav>
      <div className="flex gap-5 justify-center font-bold whitespace-nowrap">
        <div className="justify-center my-auto">아주대학교</div>
        <a href="/profile">
          <img
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/c9e3048d2ecf88eda8221e80e1f3587f63212f6776bfaae61a153b6c70100a59?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
            alt="Profile"
            className="shrink-0 w-10 aspect-square"
          />
        </a>
      </div>
    </header>
  );
}
export default Header;
