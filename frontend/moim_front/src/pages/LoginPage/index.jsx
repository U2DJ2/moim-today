import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
function LoginPage() {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1">
      <AuthLeft className="flex flex-col w-full" />
      <AuthRight />
      <div className="text-white absolute right-0 font-Praise text-9xl">
        moim
      </div>
    </div>
  );
}

export default LoginPage;
