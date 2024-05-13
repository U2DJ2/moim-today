import React from "react";

function CardComponent({ date, dday, title, btn, clickHandler }) {
  return (
    <div
      className="py-8 w-fit justify-items-start p-4 grid font-Pretendard_Medium text-xl gap-4 bg-white shadow-[0px_1px_4px_rgba(0,_0,_0,_0.25)] rounded-3xl hover:cursor-pointer"
      onClick={clickHandler}
    >
      <div className="flex gap-3">
        <div className=" ">{date}</div>
        <div className=" text-scarlet">{dday}</div>
        {btn && (
          <button className="text-black bg-scarlet rounded-xl text-base font-Pretendard_Medium px-2 hover:text-white">
            참석하기
          </button>
        )}
      </div>
      <div className=" font-Pretendard_SemiBold">{title}</div>
    </div>
  );
}

export default CardComponent;
