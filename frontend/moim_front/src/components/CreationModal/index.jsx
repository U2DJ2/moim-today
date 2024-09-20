// React
import { useRef, useEffect, useState } from "react";

// API
import { POST } from "../../utils/axios";

// Components
import AlertModal from "../NewModal";


function CreationModal({
  showModal,
  setShowModal,
  children,
  isMeeting,
  moimId,
  startDateTime,
  endDateTime,
  agenda,
  place,
  setOpen,
  scheduleTitle,
  closeHandler,
  meetingCategory,
  isRefresh,
  setIsRefresh,
}) {
  const modalRef = useRef();

  useEffect(() => {
    console.log("시작 : " + startDateTime + " 종료 : " + endDateTime);

    // eslint-disable-next-line
  }, []);

  const [isAlertOpen, setAlertOpen] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const createMeeting = async () => {
    const data = {
      moimId: parseInt(moimId),
      agenda: agenda,
      startDateTime: startDateTime.replace("T", " ").split("+")[0],
      endDateTime: endDateTime.replace("T", " ").split("+")[0],
      place: place,
      meetingCategory: meetingCategory,
    };
    console.log("create Meeting");
    try {
      await POST("api/meetings", data);
      setIsRefresh(true);
      setOpen(true);
    } catch (e) {
      setAlertMessage(e.response.data.message);
      setAlertOpen(true);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    if (isMeeting) {
      createMeeting();
    }
    if (closeHandler) closeHandler();
  };

  const modalOutSideClick = (e) => {
    if (modalRef.current === e.target) setShowModal(false);
  };

  return (
    <>
      <AlertModal show={isAlertOpen} size="sm" onClose={() => setAlertOpen(false)}>
        <div className="text-sm font-Pretendard_Light">{alertMessage}</div>
        <div className="flex justify-center pt-4">
          <button
            className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
            onClick={() => {
              setAlertOpen(false);
            }}
          >
            확인
          </button>
        </div>
      </AlertModal>
      <div
        className={`modal-container fixed top-0 left-0 z-50 w-screen h-screen bg-slate-400 bg-opacity-50 scale-100 flex ${showModal ? "active" : ""
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
    </>
  );
}

export default CreationModal;
