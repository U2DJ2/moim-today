import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateCalendar } from "@mui/x-date-pickers/DateCalendar";

import { useState } from "react";
import dayjs from "dayjs";

function MiniCalendar({ onSelectDate }) {
  // Get current date
  var today = new Date();

  const [value, setValue] = useState(dayjs(today));

  const handleDateChange = (newValue) => {
    setValue(newValue);
    onSelectDate(newValue); // 선택된 날짜를 상위 컴포넌트로 전달
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DateCalendar value={value} onChange={handleDateChange} />
    </LocalizationProvider>
  );
}
export default MiniCalendar;
