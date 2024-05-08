import React from "react";
import DetailedLeft from "../../DetailedLeft";
import DetailedRight from "../../DetailedRight";

function MoimContainer({
  userName,
  currentCount,
  capacity,
  category,
  title,
  contents,
  joined,
}) {
  return (
    <div className="bg-gradient-to-b from-white to-[#F6F8FE] min-h-screen ">
      <div className="grid grid-flow-col gap-9 px-24 min-h-screen">
        <DetailedLeft
          userName={userName}
          title={title}
          currentCount={currentCount}
          capacity={capacity}
          joined={joined}
        />
        <DetailedRight
          category={category}
          title={title}
          currentCount={currentCount}
          capacity={capacity}
          contents={contents}
          className={"pl-3"}
        />
      </div>
    </div>
  );
}

export default MoimContainer;
