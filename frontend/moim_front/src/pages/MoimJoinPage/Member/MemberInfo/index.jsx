import React from "react";

function MemberInfo({ profileImg }) {
  return (
    <div className="flex justify-items-center items-center gap-7 pl-11">
      <img src={profileImg} className="w-12 h-12" />
      <div className="font-Pretendard_Normal text-2xl">김유림</div>
    </div>
  );
}

export default MemberInfo;
