// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";

// Components
import { Datepicker } from "flowbite-react";
import Calendar from "../../../components/Calendar/PersonalCalendar";

// API
import { fetchMembers } from "../../../api/moim";

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
      title: "px-2 py-3 text-center font-semibold text-gray-900 dark:text-white",
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
        today: "bg-rose-600 text-white hover:bg-rose-600 dark:bg-rose-600 dark:hover:bg-rose-600",
        clear: "border border-gray-300 bg-white text-gray-900 hover:bg-gray-100 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600",
      },
    },
  },
  views: {
    days: {
      header: {
        base: "mb-1 grid grid-cols-7",
        title: "h-6 text-center text-sm font-medium leading-6 text-gray-500 dark:text-gray-400",
      },
      items: {
        base: "grid w-64 grid-cols-7",
        item: {
          base: "block flex-1 cursor-pointer rounded-full border-0 text-center text-sm font-semibold leading-9 text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-600",
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

function AvailableTime({ moimId }) {
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split("T")[0]);
  const [members, setMembers] = useState([]);
  const [memberSchedule, setMemberSchedule] = useState([]);
  const [selectedMemberId, setSelectedMemberId] = useState(null);

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date.toISOString().split("T")[0]);
  };

  const handleShowAll = () => {
    // 전체 보기 기능 추가
    setSelectedMemberId(null);
  };

  const GetMembers = async () => {
    try {
      const result = await fetchMembers(moimId);
      setMembers(result.data.moimMembers);
    } catch (e) {
      console.log(e);
    }
  };

  const onClickMember = (member) => {
    setSelectedMemberId(member.memberId);
  };

  const { MoimId } = useParams();

  useEffect(() => {
    GetMembers(moimId);
    // eslint-disable-next-line
  }, [moimId]);

  return (
    <div className="flex flex-col md:flex-row gap-5 h-full w-full">
      <div className="flex flex-col md:flex-1 items-center">
        <Datepicker
          showTodayButton={false}
          showClearButton={false}
          theme={calendarTheme}
          inline
          onSelectedDateChanged={handleMiniCalendarDateSelect}
        />
        <button
          className="w-52 justify-center px-6 py-3 text-[16px] text-center text-white bg-black whitespace-nowrap rounded-full font-semibold  hover:cursor-pointer"
          onClick={handleShowAll}
        >
          전체 보기
        </button>
        <div className="grid justify-items-center gap-3 pt-4">
          {members != null
            ? members.map((member, index) => (
                <div
                  key={index}
                  className="flex justify-items-center gap-2 cursor-pointer focus:text-scarlet"
                  onClick={() => onClickMember(member)}
                >
                  <img
                    className="focus:cursor-pointer w-6 h-6 rounded-full"
                    src={member.profileImageUrl}
                  />
                  <div
                    className={`flex font-Pretendard_Light focus:text-scarlet ${selectedMemberId === member.memberId ? "text-scarlet" : null}`}
                  >
                    {member.memberName}
                  </div>
                </div>
              ))
            : null}
        </div>
      </div>

      <div className="flex-[3_3_0%] w-full">
        <Calendar
          selectedDate={selectedDate}
          isPersonal={false}
          isAvailable={true}
          isMeeting={false}
          moimId={MoimId}
          memberId={selectedMemberId}
          events={memberSchedule}
        />
      </div>
    </div>
  );
}

export default AvailableTime;
