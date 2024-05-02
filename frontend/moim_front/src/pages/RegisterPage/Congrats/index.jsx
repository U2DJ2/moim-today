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
      <Button
        name="바로 시작하기"
        textColor={"white"}
        bgColor={"black"}
        onClick={() => setStep(step + 1)}
      />
    </div>
  );
}

export default Congrats;
