import React, { useState } from "react";
import MiniCalendar from "../ToDo/MiniCalendar";
import Calendar from "../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";

function AvailableTime() {
  const [selectedDate, setSelectedDate] = useState(null);

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };

  const { MoimId } = useParams();
  return (
    <div className="flex gap-5 h-full w-full">
      <MiniCalendar onSelectDate={handleMiniCalendarDateSelect} />
      <Calendar
        selectedDate={selectedDate}
        isAvailable={true}
        moimId={MoimId}
      />
    </div>
  );
}

export default AvailableTime;
