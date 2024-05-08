import React from "react";
import profileImg from "../../../assets/svg/profileImg.svg";
import Dropdown from "../../../components/Dropdown/Simple";
import MemberInfo from "./MemberInfo";
function Member() {
  const handleDropdown = (option) => {
    console.log(option);
  };
  return (
    <div className="grid gap-16">
      <div className="grid gap-7">
        <Dropdown options={["소유자 (1)"]} onSelect={handleDropdown} />
        <MemberInfo profileImg={profileImg} />
      </div>
      <div className="grid gap-7">
        <Dropdown options={["구성원 (4)"]} onSelect={handleDropdown} />
        <MemberInfo profileImg={profileImg} />
        <MemberInfo profileImg={profileImg} />
        <MemberInfo profileImg={profileImg} />
        <MemberInfo profileImg={profileImg} />
      </div>
    </div>
  );
}

export default Member;
