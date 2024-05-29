// React
import { useState } from "react";

// API
import { POST } from "../../utils/axios";

// Components
import Calendar from "../../components/Calendar/PersonalCalendar";
import CreationModal from "../../components/CreationModal";


export default function ProfileSection({ selectedDate }) {
  const [startDateTime, setStartDateTime] = useState("");
  const [endDateTime, setEndDateTime] = useState("");
  const [scheduleTitle, setScheduleTitle] = useState("");
  const [showModal, setShowModal] = useState(false);

  const postSchedule = () => {
    const data = {
      scheduleName: scheduleTitle,
      startDateTime: startDateTime.replace("T", " ").split("+")[0],
      endDateTime: endDateTime.replace("T", " ").split("+")[0],
    };
    try {
      const response = POST("api/schedules", data);
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

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
      {showModal ? (
        <CreationModal
          showModal={showModal}
          startDateTime={startDateTime}
          endDateTime={endDateTime}
          setShowModal={setShowModal}
          scheduleTitle={scheduleTitle}
          closeHandler={postSchedule}
        >
          <div className=" font-Pretendard_Light mx-auto">
            <div className="flex flex-col  gap-4">
              <h2 className="text-2xl font-Pretendard_Black text-start">
                개인 스케줄 입력
              </h2>
              <div className="flex flex-col gap-3">
                <div className="text-start font-Pretendard_Light">
                  스케쥴 제목
                </div>
                <input
                  className=" focus:outline-none border p-4"
                  type="text"
                  value={scheduleTitle}
                  onChange={(e) => setScheduleTitle(e.target.value)}
                  placeholder="스케줄 제목을 입력해주세요"
                />
              </div>
            </div>
          </div>
        </CreationModal>
      ) : null}
    </section>
  );
}
