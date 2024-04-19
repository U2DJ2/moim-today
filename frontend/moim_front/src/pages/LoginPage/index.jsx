import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
function LoginPage() {
  const [password, setPassword] = useState("");
  const passwordHandler = (e) => setPassword(e.target.value);
  return (
    <div>
      <AuthLeft
        title={"Log In"}
        firstContent={"Welcome back! "}
        secondContent={"Please login to your account."}
      />
      <div>
        <p className=" font-Pretendard_Normal">Email</p>
        <input
          type="email"
          name="email"
          placeholder="이메일을 입력해주세요."
          className={`border-b border-[#D9D9D9] text-black text-sm rounded-lg focus:outline-none block w-full p-2.5 mt-6 mb-12 ${
            password && "font-mono"
          }`}
          value={password}
          onChange={passwordHandler}
        />
      </div>
      <div>
        <p className=" font-Pretendard_Normal">Password</p>
        <input
          type="password"
          name="password"
          placeholder="비밀번호를 입력해주세요."
          className={`border-b border-[#D9D9D9] text-black text-sm rounded-lg focus:outline-none block w-full p-2.5 mt-6 mb-12 ${
            password && "font-mono"
          }`}
          value={password}
          onChange={passwordHandler}
        />
      </div>

      <AuthRight />
    </div>
  );
}

export default LoginPage;
