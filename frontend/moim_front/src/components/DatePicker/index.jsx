import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import { useState } from "react";
import { format } from "date-fns";

function DatePicker({ startDate, setStartDate, endDate, setEndDate }) {
  return (
    <div className="flex">
      <div className="grid">
        <p className="font-Pretendard_Light text-lg">시작 기간</p>
        <ReactDatePicker
          selected={startDate}
          onChange={(date) => setStartDate(format(date, "yyyy-MM-dd"))}
          selectsStart
          startDate={startDate}
          endDate={endDate}
          dateFormat="yyyy년 MM월 dd일"
          placeholderText="시작 날짜를 선택해주세요"
          className=" font-Pretendard_Normal focus:outline-none"
        />
      </div>
      <div className="grid">
        <p className="font-Pretendard_Light text-lg">종료 기간</p>
        <ReactDatePicker
          selected={endDate}
          onChange={(date) => setEndDate(format(date, "yyyy-MM-dd"))}
          selectsEnd
          startDate={startDate}
          endDate={endDate}
          minDate={startDate}
          dateFormat="yyyy년 MM월 dd일"
          placeholderText="종료 날짜를 선택해주세요"
          className=" font-Pretendard_Normal focus:outline-none"
        />
      </div>
    </div>
  );
}
export default DatePicker;
