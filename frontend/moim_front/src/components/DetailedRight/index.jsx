import React from "react";
import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";
function DetailedRight({ category, title, currentCount, capacity, contents }) {
  return (
    <div className="flex flex-col bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] w-full rounded-3xl px-24 gap-8">
      <div>
        <div className="bg-black font-Pretendard_Light text-xl rounded-full py-2 text-white justify-center items-center text-center w-20 mb-3">
          {category}
        </div>
        <div className=" font-Pretendard_Black text-5xl text-slate-800 mb-2">
          {title}
        </div>
        <div className="flex gap-4">
          <div className="flex gap-1 font-Pretendard_SemiBold text-lg text-[#6F6F6F]">
            <img src={icon} />
            <div className="flex">
              <div>{currentCount}</div>
              <div>/</div>
              <div>{capacity}</div>
            </div>
          </div>
          <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F] gap-2 w-full items-center">
            <img src={clock} className=" w-4" />
            <div className="inline-block w-full text-left text-lg text-[#6F6F6F]">
              가용시간 보기
            </div>
          </div>
        </div>
      </div>
      <div className="flex font-Pretendard_Normal text-darkslategray text-2xl">
        {contents}
      </div>
    </div>
  );
}

export default DetailedRight;
