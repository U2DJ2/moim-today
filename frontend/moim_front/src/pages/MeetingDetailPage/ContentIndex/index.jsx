import React from "react";

function ContentIndex({ image, indexName, number }) {
  return (
    <div className="flex font-Pretendard_Medium text-sm items-center gap-2">
      <img src={image} />
      <div className="flex gap-1">
        <div className="  text-[#FE7F8F] t">{indexName}</div>
        <div className=" text-slate-500">{number}</div>
      </div>
    </div>
  );
}

export default ContentIndex;
