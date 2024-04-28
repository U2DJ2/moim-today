import React from "react";
import AuthLeft from "../../../components/AuthLeft";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import { useForm } from "react-hook-form";
import Button from "../../../components/Button";
function Account() {
  const { register, handleSubmit, errors, formState, setValue } = useForm();
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  return (
    <div className="flex-1 flex flex-col items-start justify-center w-96 gap-16">
      <AuthTitle
        className="flex flex-col w-full"
        title={"Register"}
        firstContent={"모임 투데이에 온 것을 환영합니다!"}
        secondContent={"회원가입을 위해 이메일과 비밀번호를 입력해주세요."}
        white={true}
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
            {...register("email", { required: true, pattern: emailPattern })}
          />
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
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block mb-12 placeholder:text-white `}
            {...register("password", {
              required: true,
            })}
          />
        </div>
        <div className="flex gap-8">
          <Button name={"취소"} textColor={"gray"} bgColor={"white"} />
          <Button name={"다음"} textColor={"[#646464]"} bgColor={"white"} />
        </div>
      </div>
    </div>
  );
}

export default Account;
