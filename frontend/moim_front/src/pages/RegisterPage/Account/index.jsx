import React, { useState } from "react";
import AuthLeft from "../../../components/AuthLeft";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import { set, useForm } from "react-hook-form";
import Button from "../../../components/Button";
import { useNavigate } from "react-router";
function Account({ setActiveNext, onSubmit }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
    formState,
    setValue,
    data,
  } = useForm();
  const [clicked, setClicked] = useState(false);
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  const navigate = useNavigate();

  const cancelBtnHandler = () => {
    return navigate(-1);
  };
  const nextClick = () => {};
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
        <form onSubmit={handleSubmit(onSubmit)}>
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
              {...register("email", { required: true })}
            />
            {errors.email && <div>error occured</div>}
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
              {...register("password", { required: true })}
              className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block mb-12 placeholder:text-white `}
            />
          </div>
        </form>
      </div>
    </div>
  );
}

export default Account;
