import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
import { POST } from "../../utils/axios";
function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  // const data = {
  //   email: email,
  //   password: password,
  // };

  // POST("api/login", data).then((res) => {
  //   console.log(res.data);
  // });
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-52">
      <AuthLeft
        className="flex flex-col w-full"
        title={"Login"}
        firstContent={"Welcome back!"}
        secondContent={"Please login to your account."}
        titleColor={"black"}
        contentColor={"black"}
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
      />
      <AuthRight textColor={"white"} cardColor={"scarlet"} />
    </div>
  );
}

export default LoginPage;
