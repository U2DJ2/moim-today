import React from "react";

function CardComponent({ date, dday, title }) {
  return (
    <div className=" w-60 justify-items-start p-4 grid font-Pretendard_Medium text-xl gap-4 bg-white shadow-[0px_1px_4px_rgba(0,_0,_0,_0.25)] rounded-3xl hover:cursor-pointer">
      <div className="flex gap-3">
        <div className=" ">{date}</div>
        <div className=" text-scarlet">{dday}</div>
      </div>
      <div className="">{title}</div>
    </div>
  );
}

export default CardComponent;
