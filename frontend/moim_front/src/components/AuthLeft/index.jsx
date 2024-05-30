import { useState } from "react";
import { useNavigate } from "react-router";
import { POST } from "../../utils/axios";
import AuthTitle from "../Authentification/AuthTitle";
import checked from "../../assets/svg/checked.svg";
import unchecked from "../../assets/svg/unchecked.svg";

function AuthLeft({
  title,
  firstContent,
  secondContent,
  titleColor,
  contentColor,
  email,
  setEmail,
  password,
  setPassword,
  setOpenAlertModal,
  setMessage,
  className,
}) {
  const [memory, setMemory] = useState(true);
  const emailHandler = (e) => setEmail(e.target.value);
  const passwordHandler = (e) => setPassword(e.target.value);
  const memoryHandler = () => setMemory(!memory);
  const navigation = useNavigate();

  const onLoginClick = () => {
    const data = {
      email: email,
      password: password,
      isKeepLogin: memory ? true : false,
    };
    POST("api/login", data)
      .then((res) => {
        console.log(res);
        navigation("/");
      })
      .catch((error) => {
        const errorCode = error.response.data.statusCode;

        if (errorCode === "404") {
          setOpenAlertModal(true);
          setMessage(error.response.data.message);
        } else {
          setOpenAlertModal(true);
          setMessage(error.message);
        }
      });
  };

  return (
    <div className={`${className}`}>
      <div className="w-full max-w-lg">
        <AuthTitle
          title={title}
          firstContent={firstContent}
          secondContent={secondContent}
          titleColor={titleColor}
          contentColor={contentColor}
        />
        <div className="pt-6 pb-6">
          <p className="font-Pretendard_SemiBold block text-lg text-[#575757]">
            이메일
          </p>
          <input
            type="email"
            name="email"
            placeholder="이메일을 입력해주세요."
            autoComplete="off"
            className="border-b border-[#575757] font-Pretendard_Light text-black text-xl pt-2 pb-2 focus:outline-none w-full"
            value={email}
            onChange={emailHandler}
          />
        </div>
        <div className="pb-6">
          <p className="font-Pretendard_SemiBold text-lg text-[#575757]">
            비밀번호
          </p>
          <input
            type="password"
            name="password"
            placeholder="비밀번호를 입력해주세요."
            autoComplete="off"
            className={`border-b border-[#575757] font-Pretendard_Light text-black text-xl pt-2 pb-2 focus:outline-none w-full ${
              password && "font-mono"
            }`}
            value={password}
            onChange={passwordHandler}
          />
        </div>
        <div
          className="flex items-center font-Pretendard_Normal hover:cursor-pointer"
          onClick={memoryHandler}
        >
          <img src={memory ? checked : unchecked} alt="memory" />
          <p className="ml-2">로그인 정보를 기억하기</p>
        </div>
        <div className="pt-6 grid grid-cols-2 gap-4">
          <div className="w-auto">
            <button
              className="py-3 w-full text-xl font-semibold text-white bg-neutral-800 rounded-[50px] font-Pretendard_Black hover:cursor-pointer"
              onClick={() => navigation("/register")}
            >
              회원가입
            </button>
          </div>
          <div className="w-auto">
            <button
              className="py-3 w-full text-xl font-semibold text-white bg-scarlet rounded-[50px] font-Pretendard_Black hover:cursor-pointer"
              onClick={onLoginClick}
            >
              로그인
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AuthLeft;
