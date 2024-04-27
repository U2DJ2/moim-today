import React from "react";
import RegisterLayout from "../../components/PageContainer/RegisterContainer";
import AuthLeft from "../../components/AuthLeft";
import AuthTitle from "../../components/Authentification/AuthTitle";
import AuthRight from "../../components/AuthRight";
import { useState } from "react";
function RegisterPage() {
  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28 bg-scarlet">
      {
        <AuthLeft
          className="flex flex-col w-full"
          title={"Register"}
          firstContent={"모임 투데이에 온 것을 환영합니다!"}
          secondContent={"이메일과 비밀번호를 입력해주세요."}
          white={true}
        />
      }
      <AuthRight textColor={"black"} cardColor={"white"} />
    </div>
  );
}

export default RegisterPage;
