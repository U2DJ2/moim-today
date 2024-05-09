// React
import { useState } from "react";

// Date Picker
import "@codi10/react-modern-calendar-datepicker/lib/DatePicker.css";
import DatePicker, { utils } from "@codi10/react-modern-calendar-datepicker";


const DateRangePicker = () => {
  // ✅ a change in default state: { from: ..., to: ... }
  const [selectedDayRange, setSelectedDayRange] = useState({
    from: null,
    to: null
  });

  return (
    <DatePicker
      value={selectedDayRange}
      onChange={setSelectedDayRange}
      minimumDate={utils().getToday()}
      inputPlaceholder="Select a day"
      shouldHighlightWeekends
    />
  );
};

export default DateRangePicker;