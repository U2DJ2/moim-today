import React, { useState } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";
import DateRangePicker from "../../../components/DatePicker/Range";

function TimeTable({
  everytimeUrl,
  setEverytimeUrl,
  setStartDate,
  setEndDate,
}) {
  const onChangeHandler = (e) => {
    setEverytimeUrl(e.target.value);
  };
  const handleDateRange = (dateRange) => {
    console.log(dateRange);
    setStartDate(dateRange.startDate);
    setEndDate(dateRange.endDate);
  };

  const commonInputStyle =
    "justify-center px-4 py-3.5 text-sm font-Pretendard_Medium leading-5.5 rounded-xl bg-neutral-50 text-black";
  return (
    <div className="flex flex-col gap-16 ">
      <AuthTitle
        title={"Timetable"}
        firstContent={"마지막 단계입니다!"}
        secondContent={"에브리타임 시간표 주소를 입력해주세요."}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex flex-col gap-16">
        <div className="gap-8">
          <p className=" font-Pretendard_Black block text-xl pb-4 text-white">
            학기 기간을 선택해주세요.
          </p>

          <DateRangePicker
            onChange={handleDateRange}
            inputClassName={`w-full flex ${commonInputStyle}`}
            useMinDate={false}
          />
        </div>
        <div className="gap-1">
          <p className=" font-Pretendard_Black block text-xl text-white">
            에브리타임 시간표 주소
          </p>
          <input
            type="text"
            name="everytimeUrl"
            autoComplete="off"
            placeholder="에브리타임 시간표 url을 복사해 붙여주세요."
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block mb-12 placeholder:text-white `}
            onChange={onChangeHandler}
          />
        </div>
      </div>
    </div>
  );
}

export default TimeTable;
