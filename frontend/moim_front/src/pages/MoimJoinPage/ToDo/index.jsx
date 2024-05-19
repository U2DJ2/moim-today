import { useState } from "react";
import MonthlyTodo from "./MonthlyTodo";

function ToDo() {
  const [selectedDate, setSelectedDate] = useState(null);
  const [selected, setSelected] = useState("김유림");

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };
  return (
    <div>
      <div className="flex gap-12 font-Pretendard_Light ">
        <div
          className={`justify-center rounded-full border px-4 max-md:px-5 cursor-pointer ${selected === "HOME" ? "text-scarlet border-scarlet" : "border-black"
            }`}
          onClick={() => setSelected("HOME")}
        >
          김유림
        </div>
        <div
          className={`justify-center rounded-full border px-4  max-md:px-5 cursor-pointer ${selected === "되는시간"
              ? "text-scarlet border-scarlet"
              : "border-black"
            }`}
          onClick={() => setSelected("되는시간")}
        >
          김유림
        </div>
        <div
          className={`justify-center rounded-full border px-4  max-md:px-5 cursor-pointer ${selected === "ToDo" ? "text-scarlet border-scarlet" : "border-black"
            }`}
          onClick={() => setSelected("ToDo")}
        >
          김유림
        </div>
        <div
          className={`justify-center rounded-full border px-4 max-md:px-5 cursor-pointer ${selected === "멤버" ? "text-scarlet border-scarlet" : "border-black"
            }`}
          onClick={() => setSelected("멤버")}
        >
          김유림
        </div>
      </div>
      <div className="flex gap-5 h-full w-full">
        <div className=" w-full">
          <MonthlyTodo />
        </div>
      </div>
    </div>
  );
}

export default ToDo;
