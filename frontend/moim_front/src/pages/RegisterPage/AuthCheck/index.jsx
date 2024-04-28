import React from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";

function AuthCheck() {
  return (
    <div>
      <div>
        <AuthTitle
          className="flex flex-col w-full"
          title={"Vertification"}
          firstContent={"메일함을 확인해주세요!"}
          secondContent={"이메일 인증을 완료한 후 다음버튼을 클릭해주세요!"}
          white={true}
        />
      </div>
    </div>
  );
}

export default AuthCheck;
