import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';

// API
import axios from 'axios';

// UI
import { Datepicker, Accordion } from "flowbite-react"
import { Checkbox, Label } from 'flowbite-react';

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

const accordionTheme = {
  "root": {
    "base": "divide-y divide-gray-200 border-gray-200 dark:divide-gray-700 dark:border-gray-700",
    "flush": {
      "off": "rounded-lg border",
      "on": "border-b"
    }
  },
  "content": {
    "base": "p-5 first:rounded-t-lg last:rounded-b-lg dark:bg-gray-900"
  },
  "title": {
    "arrow": {
      "base": "h-6 w-6 shrink-0",
      "open": {
        "off": "",
        "on": "rotate-180"
      }
    },
    "base": "flex w-full items-center justify-between p-5 text-left font-medium text-gray-500 first:rounded-t-lg last:rounded-b-lg dark:text-gray-400",
    "flush": {
      "off": "hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 dark:hover:bg-gray-800 dark:focus:ring-gray-800",
      "on": "bg-transparent dark:bg-transparent"
    },
    "heading": "",
    "open": {
      "off": "",
      "on": "bg-gray-100 text-gray-900 dark:bg-gray-800 dark:text-white"
    }
  }
}

async function fetchAllTodos(startDate, setMoimData) {
    try {
        const response = await axios.get(
            `https://api.moim.today/api/todos?startDate=${startDate}&months=12`,
            {
                headers: {
                    "Content-Type": "application/json",
                }
            }
        );
        setMoimData(response.data.data);

        console.log(response.data.data);
    } catch (error) {
        console.error("Error fetching events:", error);
    }
}

async function addTodo(moimId, contents, todoDate, setMoimData) {
    try {
        const response = await axios.post(
            'https://api.moim.today/api/todos',
            {
                moimId: moimId,
                contents: contents,
                todoDate: todoDate
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                },
            }
        );

        console.log('Todo added successfully:', response.data);

         // 추가한 후에 todo 리스트 다시 불러오기
         fetchAllTodos(todoDate.slice(0, 7), setMoimData);
    } catch (error) {
        console.error('Error adding todo:', error);
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

function Sidebar({ onDateChange }) {
    const navigate = useNavigate();
    const [moimData, setMoimData] = useState([]);

    useEffect(() => {
        const firstDate = new Date(new Date().getFullYear(), 0, 1).toISOString().slice(0, 7);
        fetchAllTodos(firstDate, setMoimData);
    }, []); // Empty dependency array to ensure useEffect runs only once

    const handleTodoCheckboxClick = (todo) => {
        alert(`Clicked on todo: ${todo.contents}`);
    };

    const handleHome = () => {
        navigate('/');
    };

    const handleAddTodo = () => {
        const moimId = prompt('Enter moimId:');
        const contents = prompt('Enter contents:');
        const todoDate = prompt('Enter todoDate:');

        if (!moimId || !contents || !todoDate) {
            alert('Please fill in all fields.');
            return;
        }

        addTodo(moimId, contents, todoDate, setMoimData);
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
                <Datepicker showTodayButton={false} showClearButton={false} theme={calendarTheme} inline onSelectedDateChanged={onDateChange} />
                <div className="flex gap-2.5 mt-5"></div>
                <button
                    className='w-52 justify-center px-6 py-3 text-[16px] text-center text-white bg-black whitespace-nowrap rounded-full font-semibold  hover:cursor-pointer'
                    onClick={handleAddTodo}>
                    TODO 추가하기
                </button>
                <div className="flex gap-2.5 mt-5"></div>
                {moimData.map((moim) => (
                    <Accordion key={moim.moimId} className='w-72' theme={accordionTheme} >
                        <Accordion.Panel>
                            <Accordion.Title>{moim.moimTitle} (ID : {moim.moimId})</Accordion.Title>
                            <Accordion.Content>
                                {moim.todoResponses.map((todo, todoIndex) => {
                                    const isLastTodo = todoIndex === moim.todoResponses.length - 1;
                                    return (
                                        <div key={todo.todoId} className={`flex items-center gap-2${isLastTodo ? '' : ' mb-4'}`}>
                                            <Checkbox onChange={() => handleTodoCheckboxClick(todo)} />
                                            <Label>{todo.contents}</Label>
                                        </div>
                                    );
                                })}
                            </Accordion.Content>
                        </Accordion.Panel>
                    </Accordion>
                ))}
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
        <div className="justify-between bg-white flex flex-col">
            <div className="flex w-full max-md:flex-col max-md:gap-0">
                <Sidebar onDateChange={handleMiniCalendarDateSelect} />
                <PersonalSection selectedDate={selectedDate} />
            </div>
        </div>
    );
}
