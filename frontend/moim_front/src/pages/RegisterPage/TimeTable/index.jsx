import React from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import { useForm } from "react-hook-form";
function TimeTable() {
  const { register, handleSubmit, errors, formState, setValue, data } =
    useForm();
  return (
    <div className="flex flex-col gap-16">
      <AuthTitle
        title={"Timetable"}
        firstContent={"마지막 단계입니다!"}
        secondContent={"에브리타임 시간표 주소를 입력해주세요."}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex flex-col gap-16">
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            학기 이름을 선택해주세요.
          </p>
          <input
            type="email"
            name="email"
            autoComplete="off"
            placeholder="ex) 24년도 1학기"
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white`}
            {...register("email", { required: true })}
          />
        </div>
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            에브리타임 시간표 주소
          </p>
          <input
            type="password"
            name="password"
            autoComplete="off"
            placeholder="에브리타임 시간표 url을 복사해 붙여주세요."
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block mb-12 placeholder:text-white `}
            {...register("password", {
              required: true,
            })}
          />
        </div>
      </div>
    </div>
  );
}

export default TimeTable;
