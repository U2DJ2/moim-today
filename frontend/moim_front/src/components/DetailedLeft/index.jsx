import React from "react";
import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";
import Button from "../../components/Button";
function DetailedLeft({ userName, title, currentCount, capacity }) {
  const onClickHandler = () => {
    console.log("first");
  };
  return (
    <div className="flex flex-col gap-4 items-center w-full h-full min-h-screen">
      <div className="bg-black w-96 h-60 rounded-t-2xl"></div>
      <div className=" font-Pretendard_Normal ">{userName}</div>
      <div className=" font-Pretendard_Black text-4xl text-[#3F3F3F]">
        {title}
      </div>
      <div className="flex gap-1 font-Pretendard_SemiBold text-sm text-[#6F6F6F]">
        <img src={icon} />
        <div className="flex">
          <div>{currentCount}</div>
          <div>/</div>
          <div>{capacity}</div>
        </div>
      </div>
      <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F]">
        <img src={clock} />
        <span className="ml-2">가용시간 보기</span>
      </div>
      <Button
        name={"🏳️ 참여하기"}
        textColor={"white"}
        bgColor={"black"}
        onClick={onClickHandler}
      />
    </div>
  );
}

export default DetailedLeft;
