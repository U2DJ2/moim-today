import React from "react";
import RegisterLayout from "../../components/PageContainer/RegisterContainer";
import AuthLeft from "../../components/AuthLeft";
import AuthTitle from "../../components/Authentification/AuthTitle";
import AuthRight from "../../components/AuthRight";
function RegisterPage() {
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28 ">
      <AuthLeft className="flex flex-col w-full" />
      <AuthRight textColor={"black"} cardColor={"white"} />
    </div>
  );
}

export default RegisterPage;
