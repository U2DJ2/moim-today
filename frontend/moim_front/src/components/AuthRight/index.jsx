import React from "react";
import landingImg from "../../assets/svg/landingImg.svg";
function AuthRight({ cardColor, textColor }) {
  return (
    <div className="flex-1 w-full relative">
      <div
        className={`flex w-[120%] h-[110%] absolute left-0 -rotate-18 rounded-[64px] bg-${cardColor}`}
      ></div>
      <div
        className={`text-[150px] relative font-Praise top-1/4 left-1/3 justify-center text-${textColor}`}
      >
        Moim
      </div>
      <img
        src={landingImg}
        className="absolute bottom-0 right-0 object-fill w-full h-1/2"
      />
    </div>
  );
}

export default AuthRight;
