// React
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

// Dayjs
import dayjs from 'dayjs';

// Icons
import HomeIcon from '@mui/icons-material/Home';

// Components
import PersonalSection from "./PersonalSection";

// CSS
import "./style.css";

import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';

function SidebarElementIcon() {
    return (
        <div className="flex flex-col justify-center items-start p-5 bg-rose-600 rounded-3xl max-md:pr-5">
            <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/a62114f657cd2fa44c2617cafc5a564692ee84397f85414112c4d73292a6479e?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&" alt="" className="w-7 aspect-square" />
        </div>
    );
}

function SidebarElementLink({ icon, text, color, onClick }) {
    return (
        <div className={`flex items-center gap-2.5 py-4 text-base font-semibold ${color} cursor-pointer`} onClick={onClick}>
            {icon}
            <div className="flex-1 flex items-center justify-between">{text}</div>
        </div>
    );
}

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

// Sidebar 컴포넌트
function Sidebar({ onSelectMiniCalendarDate }) {
    const navigate = useNavigate();

    // 홈 버튼 클릭 시 메인 페이지로 이동
    const handleHome = () => {
        navigate('/');
    };

    return (
        <aside className="flex flex-col w-[30%] max-md:ml-0 max-md:w-full">
            <div className="flex flex-col grow items-start self-stretch py-4 pr-3 pl-16 max-md:pl-5 max-md:mt-6">
                <SidebarElementIcon />
                <div className="mt-3 text-base font-semibold text-center text-zinc-500">
                    모임 대시보드{" "}
                </div>
                <div className="flex gap-2.5 mt-5"></div>
                <SidebarElementLink icon={<HomeIcon />} text="홈" color="text-gray-400" className="mt-16 max-md:mt-10" onClick={handleHome} />
                <MiniCalendar onSelectDate={onSelectMiniCalendarDate} />
            </div>
        </aside>
    );
}

export default function Schedule() {
    const [selectedDate, setSelectedDate] = useState(null);

    const handleMiniCalendarDateSelect = (date) => {
        setSelectedDate(date);
    };

    return (
        <div className="justify-between pt-9 bg-white h-screen flex flex-col">
            <div className="flex-1 overflow-auto">
                <div className="flex gap-5 h-full w-full max-md:flex-col max-md:gap-0">
                    <Sidebar onSelectMiniCalendarDate={handleMiniCalendarDateSelect} />
                    <PersonalSection selectedDate={selectedDate} />
                </div>
            </div>
        </div>
    );
}