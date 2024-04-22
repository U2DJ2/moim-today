import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
function LoginPage() {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative ">
      <AuthLeft className="flex flex-col pl-36" />
      <img src={right1} />
    </div>
  );
}

export default LoginPage;
