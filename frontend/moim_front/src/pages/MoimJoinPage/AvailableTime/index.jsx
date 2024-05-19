import React, { useState } from "react";
import MiniCalendar from "../ToDo/MiniCalendar";
import Calendar from "../../../components/Calendar/PersonalCalendar";

function AvailableTime() {
  const [selectedDate, setSelectedDate] = useState(null);

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };
  return (
    <div className="grid gap-5 h-full w-full">
      <MiniCalendar onSelectDate={handleMiniCalendarDateSelect} />
      <Calendar selectedDate={selectedDate} />
    </div>
  );
}

export default AvailableTime;
