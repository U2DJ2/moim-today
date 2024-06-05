import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Datepicker } from "flowbite-react";
import Calendar from "../../../components/Calendar/PersonalCalendar";
import { fetchMembers } from "../../../api/moim";

function AvailableTime({ moimId }) {
  const [selectedDate, setSelectedDate] = useState(
    new Date().toISOString().split("T")[0]
  );
  const [members, setMembers] = useState([]);
  const [memberId, setMemberId] = useState(null);
  const [memberSchedule, setMemberSchedule] = useState([]);
  const [selected, setSelected] = useState();
  const [events, setEvents] = useState([]);

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };

  const GetMembers = async () => {
    try {
      const result = await fetchMembers(moimId);
      setMembers(result.data.moimMembers);
    } catch (e) {
      console.log(e);
    }
  };

  function mapEventData(event, backgroundEvent) {
    const formattedEvent = {
      id: event.scheduleId || event.calendarId,
      title: event.scheduleName || "",
      start: event.startDateTime.replace(" ", "T"),
      end: event.endDateTime.replace(" ", "T"),
      allDay: false,
      backgroundColor: event.colorHex,
    };
    if (backgroundEvent) {
      formattedEvent.display = "background";
    }
    return formattedEvent;
  }

  useEffect(() => {
    GetMembers(moimId);
  }, []);

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

  const onClickMember = (member) => {
    console.log(member);
    setSelected(member.memberId);
    setMemberId(member.memberId);
  };

  const { MoimId } = useParams();
  return (
    <div className="flex flex-col md:flex-row gap-5 h-full w-full">
      <div className="flex flex-col md:flex-1">
        <div className="flex-1">
          <Datepicker
            showTodayButton={false}
            showClearButton={false}
            theme={calendarTheme}
            inline
            onSelectedDateChanged={handleMiniCalendarDateSelect}
          />
          <div className="grid justify-items-center gap-3 mt-4 md:mt-0">
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
                      className={`flex font-Pretendard_Light focus:text-scarlet ${
                        selected === member.memberId ? "text-scarlet" : null
                      }`}
                    >
                      {member.memberName}
                    </div>
                  </div>
                ))
              : null}
          </div>
        </div>
      </div>

      <div className="flex-[3_3_0%] w-full">
        <Calendar
          selectedDate={selectedDate}
          isPersonal={false}
          isAvailable={true}
          isMeeting={false}
          moimId={MoimId}
          memberId={memberId}
          events={memberSchedule}
        />
      </div>
    </div>
  );
}

export default AvailableTime;
