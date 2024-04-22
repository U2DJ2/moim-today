import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
function LoginPage() {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative space-x-2 gap-1">
      <AuthLeft className="flex flex-col pl-36" />
      <div className="absolute right-1 top-0 h-full">
        <img src={right1} className="object-cover h-full" />
      </div>
    </div>
  );
}

export default LoginPage;
