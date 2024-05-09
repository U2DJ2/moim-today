// React
import { useState } from "react";

// Date Picker
import Datepicker from "react-tailwindcss-datepicker";

const DateRangePicker = ({ inputClassName, onChange }) => {

    const [value, setValue] = useState({
        startDate: null,
        endDate: null
    });

    const handleValueChange = (newValue) => {
        setValue(newValue);
        onChange(newValue);
    }

    return (
        <Datepicker
            inputClassName={inputClassName}
            value={value}
            onChange={handleValueChange}
            showShortcuts={true}
        />
    );
};
export default DateRangePicker;