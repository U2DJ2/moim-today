import React from "react";
import { useNavigate } from "react-router";

function Button({ name, textColor, bgColor,  onClick }) {

  return (
    <button
      className={`w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-${textColor} bg-${bgColor} whitespace-nowrap rounded-[50px] font-Pretendard_Black font-black hover:cursor-pointer  `}
      onClick={() => onClick()}
    >
      {name}
    </button>
  );
}

export default Button;
