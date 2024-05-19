import React, { useState } from "react";
import MiniCalendar from "../ToDo/MiniCalendar";
import { Datepicker } from "flowbite-react";
import Calendar from "../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";

function AvailableTime() {
  const [selectedDate, setSelectedDate] = useState(null);

  const handleMiniCalendarDateSelect = (date) => {
    setSelectedDate(date);
  };
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

  const { MoimId } = useParams();
  return (
    <div className="flex gap-5 h-full w-full">
      <div className="flex-1">
        <Datepicker
          className="flex-1"
          showTodayButton={false}
          showClearButton={false}
          theme={calendarTheme}
          inline
          onSelectedDateChanged={handleMiniCalendarDateSelect}
        />
      </div>
      <div className="flex-[3_3_0%]">
        <Calendar
          className="flex-3"
          selectedDate={selectedDate}
          isAvailable={true}
          moimId={MoimId}
        />
      </div>
    </div>
  );
}

export default AvailableTime;
