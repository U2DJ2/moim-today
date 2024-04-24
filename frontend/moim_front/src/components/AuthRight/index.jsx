import React from "react";
import landingImg from "../../assets/svg/landingImg.svg";
function AuthRight() {
  return (
    <div className="flex-1 w-full relative">
      <div className="flex bg-scarlet w-[120%] h-[110%] absolute left-0 -rotate-18 rounded-[64px]"></div>
      <div className="text-white text-[150px] absolute font-Praise left-1/3 top-1/3">
        Moim
      </div>
      <img src={landingImg} className="absolute bottom-0 right-0" />
    </div>
  );
}

export default AuthRight;
