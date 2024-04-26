import AuthTitle from "../Authentification/AuthTitle";
import { useState } from "react";
import checked from "../../assets/svg/checked.svg";
import unchecked from "../../assets/svg/unchecked.svg";
import Button from "../Button";
function AuthLeft() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const emailHandler = (e) => setEmail(e.target.value);
  const passwordHandler = (e) => setPassword(e.target.value);

  return (
    <div className="flex-1 flex flex-col pl-28 items-start justify-center">
      <AuthTitle
        title={"Log In"}
        firstContent={"Welcome back! "}
        secondContent={"Please login to your account."}
      />
      <div className="pt-16 pb-12">
        <p className=" font-Pretendard_Normal block text-xl text-[#575757]">
          Email
        </p>
        <input
          type="email"
          name="email"
          placeholder="enter your email"
          className={`border-b border-[#575757] text-black text-xl pt-2 pb-2 focus:outline-none w-full block ${password}`}
          value={email}
          onChange={emailHandler}
        />
      </div>
      <div>
        <p className=" font-Pretendard_Normal text-xl text-[#575757]">
          Password
        </p>
        <input
          type="password"
          name="password"
          placeholder="enter your password"
          className={`border-b border-[#575757] text-black text-xl pt-2 pb-2 focus:outline-none w-full block mb-12 ${
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
      <Button name={"로그인"} />
    </div>
  );
}

export default AuthLeft;
