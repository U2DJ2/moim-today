import React, { useRef, useEffect } from "react";
import { postMeeting } from "../../api/moim";
import { parse } from "date-fns";
import axios from "axios";

function CreationModal({
  showModal,
  setShowModal,
  children,
  closeHandler,
  isMeeting,
  moimId,
  startDateTime,
  endDateTime,
  agenda,
  place,
  setOpen,
  scheduleTitle,
  meetingCategory,
}) {
  const modalRef = useRef();

  if (isMeeting) {
  }
  useEffect(() => {
    console.log(startDateTime);
    console.log(endDateTime);
  }, []);
  const closeModal = () => {
    closeHandler();
    setShowModal(false);

    if (isMeeting) {
      const data = {
        moimId: parseInt(moimId),
        agenda: agenda,
        startDateTime: startDateTime.replace("T", " ").split("+")[0],
        endDateTime: endDateTime.replace("T", " ").split("+")[0],
        place: place,
        meetingCategory: "REGULAR",
      };
      postMeeting(data);
      setOpen(true);
    } else {
      //Personal 스케쥴 등록
      console.log(endDateTime);
      // const data = {
      //   scheduleName: scheduleTitle,
      //   startDateTime: startDateTime.replace("T", " ").split("+")[0],
      //   endDateTime: endDateTime.replace("T", " ").split("+")[0],
      // };
      // try {
      //   const result = async () => await axios.post("api/schedules", data);
      //   console.log(result);
      // } catch (e) {
      //   console.log(e);
      // }
    }
  };

  const modalOutSideClick = (e) => {
    if (modalRef.current === e.target) closeModal();
  };

  return (
    <div
      className={`modal-container fixed top-0 left-0 z-50 w-screen h-screen bg-slate-400 bg-opacity-50 scale-100 flex ${
        showModal ? "active" : ""
      }`}
      ref={modalRef}
      onClick={(e) => modalOutSideClick(e)}
    >
      <div className="m-auto shadow w-96 rounded-xl">
        <div className="flex flex-col justify-center h-80 text-center text-black whitespace-pre bg-white rounded-t-xl">
          {children}
        </div>
        <button
          className="w-full h-16 text-lg text-center text-white rounded-b-xl bg-scarlet font-Pretendard_Light"
          onClick={closeModal}
        >
          확인
        </button>
      </div>
    </div>
  );
}

export default CreationModal;
