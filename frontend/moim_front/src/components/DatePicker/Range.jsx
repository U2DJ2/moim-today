// React
import { useState } from "react";

// Date Picker
import Datepicker from "react-tailwindcss-datepicker";

const DateRangePicker = ({ inputClassName, onChange, useMinDate }) => {
  const [value, setValue] = useState({
    startDate: null,
    endDate: null,
  });

  const handleValueChange = (newValue) => {
    setValue(newValue);
    onChange(newValue);
  };

  return (
    <Datepicker
      primaryColor="red"
      minDate={useMinDate ? new Date() : undefined}
      inputClassName={inputClassName}
      value={value}
      onChange={handleValueChange}
    />
  );
};
export default DateRangePicker;
