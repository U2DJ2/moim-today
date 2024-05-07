import AuthTitle from "../Authentification/AuthTitle";
import { useState } from "react";
import checked from "../../assets/svg/checked.svg";
import unchecked from "../../assets/svg/unchecked.svg";
import Button from "../Button";
import RegisterLabel from "../../assets/svg/Register_Label.svg";
import EmailBtn from "../../assets/svg/EmailBtn.svg";
import { useNavigate } from "react-router";
import { POST } from "../../utils/axios";
import Modal from "../Modal/ModalTest";
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
  setIsOpen,
  setMessage,
}) {
  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  const [memory, setMemory] = useState(true);

  const emailHandler = (e) => setEmail(e.target.value);
  const passwordHandler = (e) => setPassword(e.target.value);
  const memoryHandler = (e) => !setMemory;
  const navigation = useNavigate();
  const onClick = () => {
    const data = {
      email: email,
      password: password,
    };

    POST("api/login", data)
      .then((res) => {
        console.log(res.data);
        navigation("/");
      })
      .catch((error) => {
        console.log(error.response.data.statusCode);
        const errorCode = error.response.data.statusCode;
        if (errorCode === "404") {
          setIsOpen(true);
          setMessage(error.response.data.message);
          console.log(error.response.message);
        }
      });
  };
  return (
    <div className="flex-1 flex flex-col justify-center justify-items-center">
      <div className="w-96 ">
        <AuthTitle
          title={title}
          firstContent={firstContent}
          secondContent={secondContent}
          titleColor={titleColor}
          contentColor={contentColor}
        />
        <div className="pt-16 pb-12 w-full">
          <p className=" font-Pretendard_Normal block text-xl text-[#575757]">
            Email
          </p>
          <input
            type="email"
            name="email"
            placeholder="enter your email"
            autoComplete="off"
            className={`border-b border-[#575757] font-Pretendard_Light text-black text-xl pt-2 pb-2 focus:outline-none w-full block ${password}`}
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
            autoComplete="off"
            className={`border-b border-[#575757] font-Pretendard_Light text-black text-xl pt-2 pb-2 focus:outline-none w-full block mb-12 ${
              password && "font-mono"
            }`}
            value={password}
            onChange={passwordHandler}
          />
        </div>
        <div
          className="flex gap-1 items-center font-Pretendard_Normal font hover:cursor-pointer"
          onClick={() => {
            memoryHandler;
          }}
        >
          {memory === true ? <img src={checked} /> : <img src={unchecked} />}
          <p>로그인 정보 기억하기</p>
        </div>
        <div className="pt-7">
          <button
            className={
              "w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-white bg-scarlet whitespace-nowrap rounded-[50px] font-Pretendard_Black hover:cursor-pointer"
            }
            onClick={onClick}
          >
            로그인하기
          </button>
        </div>
        <div className="self-center items-center flex gap-3 flex-col pt-12">
          <img src={RegisterLabel} />
          <img
            src={EmailBtn}
            className="hover:cursor-pointer focus:bg-gray/40"
            onClick={() => navigation("/register")}
          />
        </div>
      </div>
    </div>
  );
}

export default AuthLeft;
