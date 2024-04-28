import React from "react";

function Button({ name, textColor, bgColor }) {
  return (
    <div
      className={`w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-${textColor} whitespace-nowrap bg-${bgColor} rounded-[50px] font-Pretendard_Black font-black hover:cursor-pointer hover:bg-black`}
    >
      {name}
    </div>
  );
}

export default Button;
