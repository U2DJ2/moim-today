import React, { useEffect } from "react";
import profileImg from "../../../assets/svg/profileImg.svg";
import Dropdown from "../../../components/Dropdown/Simple";
import MemberInfo from "./MemberInfo";
import { fetchMembers } from "../../../api/moim";
import { useParams } from "react-router";
function Member({ isHost, MoimId }) {
  const handleDropdown = (option) => {
    console.log(option);
  };
  const getMembers = async () => {
    try {
      const response = await fetchMembers(MoimId);
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    getMembers(MoimId);
  }, []);
  return (
    <div className="grid gap-16">
      <div className="grid gap-7">
        <Dropdown options={["소유자 (1)"]} onSelect={handleDropdown} />
        <MemberInfo profileImg={profileImg} />
      </div>
      <div className="grid gap-7">
        <Dropdown options={["구성원 (4)"]} onSelect={handleDropdown} />
        <div className="flex justify-items-stretch">
          <MemberInfo profileImg={profileImg} />
          {isHost ? <button>내보내기</button> : null}
        </div>
      </div>
    </div>
  );
}

export default Member;
