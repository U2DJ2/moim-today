import AuthTitle from "../Authentification/AuthTitle";
import { useState } from "react";
function AuthLeft() {
  const [password, setPassword] = useState("");
  const passwordHandler = (e) => setPassword(e.target.value);

  return (
    <div className="flex flex-col px-28 items-start justify-center overflow-hidden self-stretch">
      <AuthTitle
        title={"Log In"}
        firstContent={"Welcome back! "}
        secondContent={"Please login to your account."}
      />
      <div className="pt-16 pb-12">
        <p className=" font-Pretendard_Normal block">Email</p>
        <input
          type="email"
          name="email"
          placeholder="이메일을 입력해주세요."
          className={`border-b border-[#D9D9D9] text-black text-sm pt-2 pb-2 rounded-lg focus:outline-none w-full block ${
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
          className={`border-b border-[#D9D9D9] text-black text-sm pt-2 pb-2 rounded-lg focus:outline-none w-full block mb-12 ${
            password && "font-mono"
          }`}
          value={password}
          onChange={passwordHandler}
        />
      </div>
      <p>로그인 정보 기억하기</p>
    </div>
  );
}

export default AuthLeft;
