// React
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

// API
import axios from "axios";

// UI
import { Datepicker, Accordion, Checkbox, Modal } from "flowbite-react";
import { Label, TextInput } from "flowbite-react";

// Icons
import { LuListTodo } from "react-icons/lu";
import HomeIcon from "@mui/icons-material/Home";

// Components
import PersonalSection from "./PersonalSection";
import DatePicker from "../../components/DatePicker/Single";
import Dropdown from "../../components/Dropdown/Search";

// CSS
import "./style.css";


const calendarTheme = {
  root: {
    base: "relative",
  },
  popup: {
    root: {
      base: "absolute top-10 z-50 block pt-2",
      inline: "relative top-0 z-auto",
      inner: "inline-block rounded-lg bg-white shadow-none dark:bg-gray-700",
    },
    header: {
      base: "",
      title:
        "px-2 py-3 text-center font-semibold text-gray-900 dark:text-white",
      selectors: {
        base: "mb-2 flex justify-between",
        button: {
          base: "rounded-lg bg-white px-5 py-2.5 text-sm font-semibold text-gray-900 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600",
          prev: "",
          next: "",
          view: "",
        },
      },
    },
    view: {
      base: "p-1",
    },
    footer: {
      base: "mt-2 flex space-x-2",
      button: {
        base: "w-full rounded-3xl px-5 py-2 text-center text-sm font-medium focus:ring-4 focus:ring-cyan-300",
        today:
          "bg-rose-600 text-white hover:bg-rose-600 dark:bg-rose-600 dark:hover:bg-rose-600",
        clear:
          "border border-gray-300 bg-white text-gray-900 hover:bg-gray-100 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600",
      },
    },
  },
  views: {
    days: {
      header: {
        base: "mb-1 grid grid-cols-7",
        title:
          "h-6 text-center text-sm font-medium leading-6 text-gray-500 dark:text-gray-400",
      },
      items: {
        base: "grid w-64 grid-cols-7",
        item: {
          base: "block flex-1 cursor-pointer rounded-full border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600 ",
          selected: "bg-rose-600 text-white hover:bg-rose-600",
          disabled: "text-gray-500",
        },
      },
    },
    months: {
      items: {
        base: "grid w-64 grid-cols-4",
        item: {
          base: "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
          selected: "bg-rose-600 text-white hover:bg-rose-600",
          disabled: "text-gray-500",
        },
      },
    },
    years: {
      items: {
        base: "grid w-64 grid-cols-4",
        item: {
          base: "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
          selected: "bg-rose-600 text-white hover:bg-rose-600",
          disabled: "text-gray-500",
        },
      },
    },
    decades: {
      items: {
        base: "grid w-64 grid-cols-4",
        item: {
          base: "block flex-1 cursor-pointer rounded-lg border-0 text-center text-sm font-semibold leading-9  text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
          selected: "bg-rose-600 text-white hover:bg-rose-600",
          disabled: "text-gray-500",
        },
      },
    },
  },
};

const accordionTheme = {
  root: {
    base: "divide-y divide-gray-200 border-gray-200 dark:divide-gray-700 dark:border-gray-700",
    flush: {
      off: "rounded-lg border",
      on: "border-b",
    },
  },
  content: {
    base: "p-5 first:rounded-t-lg last:rounded-b-lg dark:bg-gray-900",
  },
  title: {
    arrow: {
      base: "h-6 w-6 shrink-0",
      open: {
        off: "",
        on: "rotate-180",
      },
    },
    base: "flex w-full items-center justify-between p-5 text-left font-medium text-gray-500 first:rounded-t-lg last:rounded-b-lg dark:text-gray-400",
    flush: {
      off: "hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 dark:hover:bg-gray-800 dark:focus:ring-gray-800",
      on: "bg-transparent dark:bg-transparent",
    },
    heading: "",
    open: {
      off: "",
      on: "bg-gray-100 text-gray-900 dark:bg-gray-800 dark:text-white",
    },
  },
};

const modalTheme = {
  root: {
    base: "fixed inset-x-0 top-0 z-50 h-screen overflow-y-auto overflow-x-hidden md:inset-0 md:h-full",
    show: {
      on: "flex bg-gray-900 bg-opacity-50 dark:bg-opacity-80",
      off: "hidden",
    },
    sizes: {
      sm: "max-w-sm",
      md: "max-w-md",
      lg: "max-w-lg",
      xl: "max-w-xl",
      "2xl": "max-w-2xl",
      "3xl": "max-w-3xl",
      "4xl": "max-w-4xl",
      "5xl": "max-w-5xl",
      "6xl": "max-w-6xl",
      "7xl": "max-w-7xl",
    },
    positions: {
      "top-left": "items-start justify-start",
      "top-center": "items-start justify-center",
      "top-right": "items-start justify-end",
      "center-left": "items-center justify-start",
      center: "items-center justify-center",
      "center-right": "items-center justify-end",
      "bottom-right": "items-end justify-end",
      "bottom-center": "items-end justify-center",
      "bottom-left": "items-end justify-start",
    },
  },
  content: {
    base: "relative h-full w-full p-4 md:h-auto",
    inner:
      "relative flex max-h-[90dvh] flex-col rounded-xl bg-white shadow dark:bg-gray-700",
  },
  body: {
    base: "flex-1 overflow-visible p-6",
    popup: "pt-0",
  },
  header: {
    base: "flex items-start justify-between rounded-t border-b mt-0.5 p-5 dark:border-gray-600",
    popup: "border-b-0 p-2",
    title:
      "pt-0.5 text-xl font-Pretendard_SemiBold text-gray-900 dark:text-white",
    close: {
      base: "ml-auto inline-flex items-center rounded-lg bg-transparent p-1.5 text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900 dark:hover:bg-gray-600 dark:hover:text-white",
      icon: "h-5 w-5",
    },
  },
  footer: {
    base: "flex items-center space-x-2 rounded-b border-gray-200 p-6 dark:border-gray-600",
    popup: "border-t",
  },
};

const textInputTheme = {
  base: "flex",
  addon:
    "inline-flex items-center rounded-l-md border border-r-0 border-gray-300 bg-gray-200 px-3 text-sm text-gray-900 dark:border-gray-600 dark:bg-gray-600 dark:text-gray-400",
  field: {
    base: "relative w-full",
    icon: {
      base: "pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3",
      svg: "h-5 w-5 text-gray-500 dark:text-gray-400",
    },
    rightIcon: {
      base: "pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3",
      svg: "h-5 w-5 text-gray-500 dark:text-gray-400",
    },
    input: {
      base: "block w-full disabled:cursor-not-allowed disabled:opacity-50",
      sizes: {
        sm: "p-2 sm:text-xs",
        md: "p-3 text-sm",
        lg: "p-4 sm:text-base",
      },
      colors: {
        gray: "border-gray-300 bg-neutral-50 text-gray-900 focus:border-cyan-500 focus:ring-cyan-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-cyan-500 dark:focus:ring-cyan-500",
        info: "border-cyan-500 bg-cyan-50 text-cyan-900 placeholder-cyan-700 focus:border-cyan-500 focus:ring-cyan-500 dark:border-cyan-400 dark:bg-cyan-100 dark:focus:border-cyan-500 dark:focus:ring-cyan-500",
        failure:
          "border-red-500 bg-red-50 text-red-900 placeholder-red-700 focus:border-red-500 focus:ring-red-500 dark:border-red-400 dark:bg-red-100 dark:focus:border-red-500 dark:focus:ring-red-500",
        warning:
          "border-yellow-500 bg-yellow-50 text-yellow-900 placeholder-yellow-700 focus:border-yellow-500 focus:ring-yellow-500 dark:border-yellow-400 dark:bg-yellow-100 dark:focus:border-yellow-500 dark:focus:ring-yellow-500",
        success:
          "border-green-500 bg-green-50 text-green-900 placeholder-green-700 focus:border-green-500 focus:ring-green-500 dark:border-green-400 dark:bg-green-100 dark:focus:border-green-500 dark:focus:ring-green-500",
      },
      withRightIcon: {
        on: "pr-10",
        off: "",
      },
      withIcon: {
        on: "pl-10",
        off: "",
      },
      withAddon: {
        on: "rounded-r-lg",
        off: "rounded-lg",
      },
      withShadow: {
        on: "shadow-sm dark:shadow-sm-light",
        off: "",
      },
    },
  },
};

const labelStyle = "mt-2.5 mb-2.5 font-Pretendard_SemiBold leading-5 text-sm text-black max-md:max-w-full";
const commonInputStyle = "justify-center px-4 py-3.5 text-sm font-Pretendard_Medium leading-5.5 rounded-xl bg-neutral-50 text-black";

async function fetchAllTodos(startDate, setMoimData) {
  try {
    const response = await axios.get(
      `https://api.moim.today/api/todos?startDate=${startDate}&months=12`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    setMoimData(response.data.data);
  } catch (error) {
    console.error("Error fetching events:", error);
  }
}

async function addTodo(moimId, contents, todoDate, setMoimData) {
  try {
    const response = await axios.post(
      "https://api.moim.today/api/todos",
      {
        moimId: moimId,
        contents: contents,
        todoDate: todoDate,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    console.log("Todo added successfully:", response.data);

    // 추가한 후에 todo 리스트 다시 불러오기
    fetchAllTodos(todoDate.slice(0, 7), setMoimData);
  } catch (error) {
    console.error("Error adding todo:", error);
  }
}

function SidebarElementIcon() {
  return (
    <div className="flex flex-col justify-center items-start p-5 bg-rose-600 rounded-3xl max-md:pr-5">
      <img
        loading="lazy"
        src="https://cdn.builder.io/api/v1/image/assets/TEMP/a62114f657cd2fa44c2617cafc5a564692ee84397f85414112c4d73292a6479e?apiKey=d805a42ceca34cfc9ccedfe9a24c9a43&"
        alt=""
        className="w-7 aspect-square"
      />
    </div>
  );
}

function SidebarElementLink({ icon, text, color, onClick }) {
  return (
    <div
      className={`flex items-center gap-2.5 py-4 text-base font-semibold ${color} cursor-pointer`}
      onClick={onClick}
    >
      {icon}
      <div className="flex-1 flex items-center justify-between">{text}</div>
    </div>
  );
}

function Sidebar({ onDateChange, setOpenAddTodoModal }) {
  const navigate = useNavigate();
  const [todoData, setTodoData] = useState([]);

  useEffect(() => {
    const firstDate = new Date(new Date().getFullYear(), 0, 1)
      .toISOString()
      .slice(0, 7);
    fetchAllTodos(firstDate, setTodoData);
  }, []);

  const handleTodoCheckboxClick = (todo) => {
    alert(`Clicked on todo: ${todo.contents}`);
  };

  const handleHome = () => {
    navigate("/");
  };

  const handleAddTodo = async () => {
    setOpenAddTodoModal(true);
  };

  return (
    <aside className="flex flex-col w-[30%] max-md:ml-0 max-md:w-full">
      <div className="flex flex-col grow items-center self-stretch p-4 max-md:pl-5 max-md:mt-2">
        <SidebarElementIcon />
        <div className="mt-3 text-base font-semibold text-center text-zinc-500">
          모임 대시보드{" "}
        </div>
        <div className="flex gap-2.5 mt-5"></div>
        <SidebarElementLink
          icon={<HomeIcon />}
          text="홈"
          color="text-gray-400"
          className="mt-16 max-md:mt-10"
          onClick={handleHome}
        />
        <Datepicker
          showTodayButton={false}
          showClearButton={false}
          theme={calendarTheme}
          inline
          onSelectedDateChanged={onDateChange}
        />
        <div className="flex gap-2.5 mt-5"></div>
        <button
          className="w-52 justify-center px-6 py-3 text-[16px] text-center text-white bg-black whitespace-nowrap rounded-full font-semibold  hover:cursor-pointer"
          onClick={handleAddTodo}
        >
          TODO 추가하기
        </button>
        <div className="flex gap-2.5 mt-5"></div>
        {todoData.map((moim) => (
          <Accordion key={moim.moimId} className="w-72" theme={accordionTheme}>
            <Accordion.Panel>
              <Accordion.Title>
                {moim.moimTitle} (ID : {moim.moimId})
              </Accordion.Title>
              <Accordion.Content>
                {moim.todoResponses.map((todo, todoIndex) => {
                  const isLastTodo =
                    todoIndex === moim.todoResponses.length - 1;
                  return (
                    <div
                      key={todo.todoId}
                      className={`flex items-center gap-2${isLastTodo ? "" : " mb-4"
                        }`}
                    >
                      <Checkbox
                        onChange={() => handleTodoCheckboxClick(todo)}
                      />
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
  const [openTodoAddModal, setOpenAddTodoModal] = useState(false);
  const [moimList, setMoimList] = useState([]); // 모임 리스트 상태 추가

  useEffect(() => {
    fetchMoimList(); // 모임 리스트를 가져오는 함수 호출
  }, []);

  const fetchMoimList = async () => {
    try {
      const response = await axios.get(`https://api.moim.today/api/moims`);
      setMoimList(response.data.data); // 모임 리스트를 상태에 저장
    } catch (error) {
      console.log(error);
    }
  };

  const handleMoimSelect = (index) => {
    console.log("Selected moim ID:", moimList[index].moimId);
  };

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };

  return (
    <>
      <Modal show={openTodoAddModal} size="xl" onClose={() => setOpenAddTodoModal(false)} theme={modalTheme}>
        <Modal.Header>TODO 추가</Modal.Header>
        <Modal.Body>
          <LuListTodo className="mx-auto mb-4 h-48 w-48 text-gray-400 dark:text-gray-200" />
          <div className="flex w-full flex-col gap-4">
            <Dropdown
              label={"모임 선택"}
              placeHolder={"모임을 선택하세요"}
              options={moimList.map((moim) => moim.title)}
              onSelect={(index) => handleMoimSelect(index)}
            />
            <div>
              <div className="mb-2 block">
                <Label
                  className="font-Pretendard_SemiBold"
                  htmlFor="base"
                  value="TODO 내용"
                />
              </div>
              <TextInput
                id="base"
                type="text"
                sizing="md"
                theme={textInputTheme}
              />
            </div>
            <div>
              <div className={labelStyle}>{"운영 시간"}</div>
              <DatePicker
                onChange={() => { }}
                inputClassName={`w-full ${commonInputStyle}`}
              />
            </div>
          </div>
        </Modal.Body>
      </Modal>
      <div className="justify-between pt-6 bg-white flex flex-col">
        <div className="flex w-full max-md:flex-col max-md:gap-0">
          <Sidebar
            onDateChange={handleMiniCalendarDateSelect}
            setOpenAddTodoModal={setOpenAddTodoModal}
          />
          <PersonalSection selectedDate={selectedDate} />
        </div>
      </div>
    </>
  );
}