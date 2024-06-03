import React, { useEffect, useState } from "react";
import profileImg from "../../../assets/svg/profileImg.svg";
import Dropdown from "../../../components/Dropdown/Simple";
import MemberInfo from "./MemberInfo";
import { fetchMembers } from "../../../api/moim";
import { useParams } from "react-router";
function Member({ isHost, MoimId }) {
  const [members, setMembers] = useState([]);
  const handleDropdown = (option) => {
    console.log(option);
  };
  const getMembers = async () => {
    try {
      const response = await fetchMembers(MoimId);
      setMembers(response.data.moimMembers);
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    getMembers(MoimId);
  }, []);
  useEffect(() => {}, [members]);
  return (
    <div className="grid gap-16">
      <div className="grid gap-7">
        <Dropdown options={["소유자"]} onSelect={handleDropdown} />
        {members.map((member, index) => {
          return member.isHost ? (
            <MemberInfo
              key={member.memberId}
              memberId={member.memberId}
              profileImg={member.profileImageUrl}
              isHost={isHost}
              name={member.memberName}
              moimId={MoimId}
              refreshMembers={getMembers}
            />
          ) : null;
        })}
      </div>
      <div className="grid gap-7">
        <Dropdown options={["구성원"]} onSelect={handleDropdown} />
        <div className="flex flex-col gap-8">
          {members.map((member, index) => {
            return (
              <MemberInfo
                key={member.memberId}
                memberId={member.memberId}
                profileImg={member.profileImageUrl}
                isHost={isHost}
                name={member.memberName}
                moimId={MoimId}
                refreshMembers={getMembers}
              />
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default Member;
