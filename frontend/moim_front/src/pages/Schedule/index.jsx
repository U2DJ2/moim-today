// React
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

import { Datepicker } from "flowbite-react"

// Icons
import HomeIcon from '@mui/icons-material/Home';

// Components
import PersonalSection from "./PersonalSection";

// CSS
import "./style.css";

const calendarTheme = {
    "root": {
        "base": "relative"
    },
    "popup": {
        "root": {
            "base": "absolute top-10 z-50 block pt-2",
            "inline": "relative top-0 z-auto",
            "inner": "inline-block rounded-lg bg-white shadow-none dark:bg-gray-700"
        },
        "header": {
            "base": "",
            "title": "px-2 py-3 text-center font-semibold text-gray-900 dark:text-white",
            "selectors": {
                "base": "mb-2 flex justify-between",
                "button": {
                    "base": "rounded-lg bg-white px-5 py-2.5 text-sm font-semibold text-gray-900 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600",
                    "prev": "",
                    "next": "",
                    "view": ""
                }
            }
        },
        "view": {
            "base": "p-1"
        },
        "footer": {
            "base": "mt-2 flex space-x-2",
            "button": {
                "base": "w-full rounded-3xl px-5 py-2 text-center text-sm font-medium focus:ring-4 focus:ring-cyan-300",
                "today": "bg-rose-600 text-white hover:bg-rose-600 dark:bg-rose-600 dark:hover:bg-rose-600",
                "clear": "border border-gray-300 bg-white text-gray-900 hover:bg-gray-100 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600"
            }
        }
    },
    "views": {
        "days": {
            "header": {
                "base": "mb-1 grid grid-cols-7",
                "title": "h-6 text-center text-sm font-medium leading-6 text-gray-500 dark:text-gray-400"
            },
            "items": {
                "base": "grid w-64 grid-cols-7",
                "item": {
                    "base": "block flex-1 cursor-pointer rounded-full border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600 ",
                    "selected": "bg-rose-600 text-white hover:bg-rose-600",
                    "disabled": "text-gray-500"
                }
            }
        },
        "months": {
            "items": {
                "base": "grid w-64 grid-cols-4",
                "item": {
                    "base": "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
                    "selected": "bg-rose-600 text-white hover:bg-rose-600",
                    "disabled": "text-gray-500"
                }
            }
        },
        "years": {
            "items": {
                "base": "grid w-64 grid-cols-4",
                "item": {
                    "base": "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
                    "selected": "bg-rose-600 text-white hover:bg-rose-600",
                    "disabled": "text-gray-500"
                }
            }
        },
        "decades": {
            "items": {
                "base": "grid w-64 grid-cols-4",
                "item": {
                    "base": "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9  text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
                    "selected": "bg-rose-600 text-white hover:bg-rose-600",
                    "disabled": "text-gray-500"
                }
            }
        }
    }
}

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

// Sidebar 컴포넌트
function Sidebar({ onDateChange }) {
    const navigate = useNavigate();

    // 홈 버튼 클릭 시 메인 페이지로 이동
    const handleHome = () => {
        navigate('/');
    };

    return (
        <aside className="flex flex-col w-[30%] max-md:ml-0 max-md:w-full">
            <div className="flex flex-col grow items-center self-stretch p-4 max-md:pl-5 max-md:mt-2">
                <SidebarElementIcon />
                <div className="mt-3 text-base font-semibold text-center text-zinc-500">
                    모임 대시보드{" "}
                </div>
                <div className="flex gap-2.5 mt-5"></div>
                <SidebarElementLink icon={<HomeIcon />} text="홈" color="text-gray-400" className="mt-16 max-md:mt-10" onClick={handleHome} />
                <Datepicker theme={calendarTheme} inline onSelectedDateChanged={onDateChange}/>
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
                <div className="flex h-full w-full max-md:flex-col max-md:gap-0">
                    <Sidebar onDateChange={handleMiniCalendarDateSelect} />
                    <PersonalSection selectedDate={selectedDate} />
                </div>
            </div>
        </div>
    );
}