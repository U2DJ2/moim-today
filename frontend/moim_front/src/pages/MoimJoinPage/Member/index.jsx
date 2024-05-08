import React from "react";
import profileImg from "../../../assets/svg/profileImg.svg";
import Dropdown from "../../../components/Dropdown/Simple";
function Member() {
  const handleDropdown = (option) => {
    console.log(option);
  };
  return (
    <div>
      <div className="grid gap-4">
        <>
          <Dropdown options={["소유자 (1)"]} onSelect={handleDropdown} />
        </>

        <div className="flex justify-items-center items-center gap-4">
          <img src={profileImg} />
          <div className="font-Pretendard_Black">김유림</div>
        </div>
      </div>
    </div>
  );
}

export default Member;
