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
}) {
  return (
    <div className="grid grid-flow-col gap-9 px-24 min-h-screen">
      <DetailedLeft
        userName={userName}
        title={title}
        currentCount={currentCount}
        capacity={capacity}
      />
      <DetailedRight
        category={category}
        title={title}
        currentCount={currentCount}
        capacity={capacity}
        contents={contents}
      />
    </div>
  );
}

export default MoimContainer;
