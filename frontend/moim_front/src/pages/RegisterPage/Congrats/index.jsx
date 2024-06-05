import React from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import Button from "../../../components/Button";
import { useNavigate } from "react-router";
function Congrats({ setStep, step }) {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col gap-10">
      <AuthTitle
        title={"Congrats!"}
        firstContent={"환영합니다!"}
        secondContent={"모임 투데이에서 값진 시간 보내시길 바랍니다."}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex justify-center">
        <button
          className="w-52 justify-center px-7 py-5 text-[22px] text-center text-white bg-black whitespace-nowrap rounded-[50px] font-Pretendard_Black font-black hover:cursor-pointer max-sm:text-lg max-sm:px-4"
          onClick={() => setStep(step + 1)}
        >
          지금 시작하기
        </button>
      </div>
    </div>
  );
}

export default Congrats;
