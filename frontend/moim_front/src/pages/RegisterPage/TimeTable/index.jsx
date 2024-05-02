import React from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
function TimeTable({ everytimeUrl, setEverytimeUrl }) {
  const onChangeHandler = (e) => {
    setEverytimeUrl(e.target.value);
  };
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
            type="text"
            name="timetableName"
            autoComplete="off"
            placeholder="ex) 24년도 1학기"
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white`}
          />
        </div>
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            에브리타임 시간표 주소
          </p>
          <input
            type="string"
            name="everytimeUrl"
            autoComplete="off"
            placeholder="에브리타임 시간표 url을 복사해 붙여주세요."
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block mb-12 placeholder:text-white `}
            value={everytimeUrl}
            onChange={onChangeHandler}
          />
        </div>
      </div>
    </div>
  );
}

export default TimeTable;
