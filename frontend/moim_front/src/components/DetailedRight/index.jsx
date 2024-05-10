import React from "react";
import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";

function DetailedRight({ category, title, currentCount, capacity, contents }) {
  return (
    <div className="flex flex-col bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] overflow-hidden max-w-[900px] rounded-3xl px-24 gap-8 min-h-[600px] h-fit">
      <div>
        <div className="bg-black font-Pretendard_Light text-lg rounded-full px-5 text-white justify-center items-center text-center w-fit mb-3">
          {category}
        </div>
        <div className=" font-Pretendard_Black text-4xl text-[#3F3F3F] mb-2">
          {title}
        </div>
        <div className="flex gap-4">
          <div className="flex gap-1 font-Pretendard_SemiBold text-xl text-[#6F6F6F] hover:cursor-pointer">
            <img src={icon} />
            <div className="flex">
              <div>{currentCount}</div>
              <div>/</div>
              <div>{capacity}</div>
            </div>
          </div>
          <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F] gap-2 w-full items-center">
            <img src={clock} className=" w-4" />
            <div className="inline-block w-full text-left text-xl text-[#6F6F6F] hover:cursor-pointer">
              가용시간 보기
            </div>
          </div>
        </div>
      </div>
      <div className="flex font-Pretendard_Light text-[#3F3F3F] text-3xl">
        {contents}
      </div>
    </div>
  );
}

export default DetailedRight;
