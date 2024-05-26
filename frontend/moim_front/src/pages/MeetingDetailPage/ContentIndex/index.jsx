import React from "react";

function ContentIndex({ image, indexName }) {
  return (
    <div className="flex items-center gap-2">
      <img src={image} />
      <div className=" font-Pretendard_Medium text-[#FE7F8F] text-sm">
        {indexName}
      </div>
    </div>
  );
}

export default ContentIndex;
