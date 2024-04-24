import AuthTitle from "../Authentification/AuthTitle";
import { useState } from "react";
import checked from "../../assets/svg/checked.svg";
import unchecked from "../../assets/svg/unchecked.svg";
function AuthLeft() {
  const [password, setPassword] = useState("");
  const passwordHandler = (e) => setPassword(e.target.value);

  return (
    <div className="flex-1 flex flex-col pl-28 items-start justify-center">
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
      <div className="flex gap-1 items-center font-Pretendard_Normal">
        <img src={unchecked} />
        <p>로그인 정보 기억하기</p>
      </div>
    </div>
  );
}

export default AuthLeft;
