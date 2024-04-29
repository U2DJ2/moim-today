import React, { useState } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";

function Personal() {
  const [gender, setGender] = useState("");
  const [optionVisible, setOptionVisible] = useState();
  const buttonName = ["남성", "여성"];
  return (
    <div className="flex flex-col gap-16">
      <AuthTitle
        title={"Personal"}
        firstContent={"이제 세번째 단계입니다."}
        secondContent={"생일과 성별을 입력해주세요!"}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex flex-col gap-8">
        <div className="flex flex-col gap-3">
          <p className=" font-Pretendard_Black block text-xl text-white">
            생년월일
          </p>
          <div className="flex gap-4 ">
            <input
              type="number"
              className="border text-wrapper border-white bg-scarlet placeholder:text-white placeholder:justify-items-center
            text-xl rounded-lg px-4 py-1 focus:outline-none w-20"
              placeholder="YYYY"
            />
            <input
              type="number"
              className="border text-wrapper border-white bg-scarlet placeholder:text-white 
         text-xl rounded-lg px-4 py-1 w-20 focus:outline-none placeholder:text-center"
              placeholder="MM"
            />
            <input
              type="number"
              className="border text-wrapper border-white bg-scarlet placeholder:text-white 
        text-xl rounded-lg px-4 py-1 w-20 focus:outline-none placeholder:text-center"
              placeholder="DD"
            />
          </div>
        </div>
        <div className="flex flex-col gap-3">
          <p className=" font-Pretendard_Black block text-xl text-white">
            성별
          </p>
          <div className="flex gap-4">
            <button
              className={`${
                gender === "male"
                  ? "border border-white bg-white text-scarlet placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none font-bold"
                  : "border border-white bg-scarlet text-white placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none"
              }`}
              onClick={() => setGender("male")}
            >
              남성
            </button>
            <button
              className={`${
                gender === "female"
                  ? "border border-white bg-white text-scarlet placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none font-bold"
                  : "border border-white bg-scarlet text-white placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none "
              }`}
              onClick={() => setGender("female")}
            >
              여성
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Personal;
