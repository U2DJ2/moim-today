import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
function LoginPage() {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28">
      <AuthLeft
        className="flex flex-col w-full"
        title={"Login"}
        firstContent={"Welcome back!"}
        secondContent={"Please login to your account."}
      />
      <AuthRight textColor={"white"} cardColor={"scarlet"} />
    </div>
  );
}

export default LoginPage;
