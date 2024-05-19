// Components
import Calendar from "../../components/Calendar/PersonalCalendar";
import { useState } from "react";

export default function ProfileSection({ selectedDate }) {
  const [startDateTime, setStartDateTime] = useState("");
  const [endDateTime, setEndDateTime] = useState("");
  const [scheduleTitle, setScheduleTitle] = useState("");
  const [showModal, setShowModal] = useState(false);

  return (
    <section className="flex flex-col w-full max-md:ml-0 max-md:w-full">
      <div className="flex flex-col self-stretch p-12 text-sm font-semibold leading-5 whitespace-nowrap bg-slate-50 rounded-[64px_0px_0px_0px] text-stone-500 max-md:px-5 max-md:mt-6 max-md:max-w-full h-fit flex-grow">
        <h1 className="text-6xl text-black max-md:max-w-full max-md:text-4xl">
          Weekly
        </h1>
        <div className="py-3"></div>
        <Calendar
          isPersonal={true}
          showModal={showModal}
          setShowModal={setShowModal}
          scheduleTitle={scheduleTitle}
          setScheduleTitle={setScheduleTitle}
          setStartDateTime={setStartDateTime}
          setEndDateTime={setEndDateTime}
        />
      </div>
    </section>
  );
}
