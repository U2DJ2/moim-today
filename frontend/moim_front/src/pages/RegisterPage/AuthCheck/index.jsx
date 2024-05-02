import React, { useEffect } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";

function AuthCheck({ emailAuth, setEmailAuth, setActiveNext }) {
  useEffect(() => {
    setActiveNext(true);
  }, [emailAuth]);

  return (
    <div>
      <div>
        <AuthTitle
          className="flex flex-col w-full"
          title={"Vertification"}
          firstContent={"메일함을 확인해주세요."}
          secondContent={"이메일 인증을 완료한 후 다음 버튼을 클릭해주세요!"}
          titleColor={"white"}
          contentColor={"white"}
          white={true}
        />
      </div>
    </div>
  );
}

export default AuthCheck;
