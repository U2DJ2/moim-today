import axios from "axios";
import { useState } from "react";
function MemberInfo({
  profileImg,
  isHost,
  name,
  moimId,
  memberId,
  refreshMembers,
}) {
  const onClickHandler = async () => {
    try {
      const result = await axios.delete(
        "https://api.moim.today/api/moims/members/kick",
        { data: { moimId: parseInt(moimId), deleteMemberId: memberId } }
      );
      console.log(result);
      refreshMembers();
    } catch (e) {
      console.log(e);
    }
  };
  return (
    <div className="flex justify-items-center items-center gap-7 pl-11">
      <img src={profileImg} className="w-12 h-12" />
      <div className="font-Pretendard_Normal text-2xl">{name}</div>
      {isHost && memberId ? (
        <button className="hover:text-scarlet" onClick={onClickHandler}>
          내보내기
        </button>
      ) : null}
    </div>
  );
}

export default MemberInfo;
