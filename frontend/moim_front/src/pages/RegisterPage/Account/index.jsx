import React, { useState, useEffect } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";

const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
const passwordPattern = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z]).{6,}$/;
function Account({ email, setEmail, password, setPassword }) {
  const [clicked, setClicked] = useState(false);
  const [validationEmail, setValidationEmail] = useState(true);
  const [validationPassword, setValidationPassword] = useState(true);

  const emailHandler = (e) => {
    //setEmailDuplication(false);
    setEmail(e.target.value);
    console.log(e.target.value);
  };
  const passwordHandler = (e) => {
    setPassword(e.target.value);
    console.log(e.target.value);
  };

  useEffect(() => {
    if (email && !emailPattern.test(email)) setValidationEmail(false);
    else setValidationEmail(true);

    if (password && !passwordPattern.test(password))
      setValidationPassword(false);
    else setValidationPassword(true);
  }, [email, password]);

  return (
    <div className="flex flex-col w-100 gap-16">
      <AuthTitle
        className="flex flex-col w-full"
        title={"Register"}
        firstContent={"모임 투데이에 온 것을 환영합니다!"}
        secondContent={"회원가입을 위해 이메일과 비밀번호를 입력해주세요."}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex flex-col gap-12">
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            Email
          </p>
          <input
            type="email"
            name="email"
            autoComplete="off"
            placeholder="enter your email"
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white`}
            value={email}
            onChange={emailHandler}
          />
          <p
            className={`mt-2 text-xs font-medium ${
              !validationEmail ? "text-white" : "text-scarlet"
            } ${validationEmail && email ? "hidden" : "block"}`}
          >
            이메일 형식이 잘못되었습니다.
          </p>
        </div>
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            Password
          </p>
          <input
            type="password"
            name="password"
            autoComplete="off"
            placeholder="enter your password"
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white `}
            value={password}
            onChange={passwordHandler}
          />
          <p
            className={`mt-2 text-xs font-medium ${
              !validationPassword ? "text-white" : "text-scarlet"
            } ${validationPassword && password ? "hidden" : "block"}`}
          >
            6자 이상이면서 영문 대소문자, 숫자, 특수문자 중 적어도 하나를
            포함해야합니다.
          </p>
        </div>
      </div>
    </div>
  );
}

export default Account;
