// React
import { useState, useEffect } from "react";
import { useParams } from "react-router";

// Components
import MoimContainer from "../../components/PageContainer/MoimContainer";
import DetailedLeft from "../../components/DetailedLeft";

// Pages
import ToDo from "./ToDo";
import Member from "./Member";
import MoimHome from "./MoimHome";
import AvailableTime from "./AvailableTime";

// API
import axios from "axios";
import { fetchMeetings, fetchMoimInfo, fetchNotices } from "../../api/moim";
import { checkWriter } from "../../api/users";

// UI
import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";
import MoimLayout from "../../components/MoimLayout";

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
    base: "flex-1 overflow-auto p-6",
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

const homeKey = "홈";
const availableTimeKey = "되는 시간";
const todoKey = "할 일";
const memberKey = "멤버";

function MoimJoinPage() {
  const [isAlertModalOpen, setAlertModalOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [selected, setSelected] = useState(homeKey);
  const [moimInfo, setMoimInfo] = useState([]);
  const [meetings, setMeetings] = useState([]);
  const [writerInfo, setWriterInfo] = useState([]);
  const [isHost, setIsHost] = useState(false);

  const { MoimId } = useParams();

  const getInfo = async () => {
    try {
      const result = await fetchMoimInfo(MoimId);
      setMoimInfo(result.data);
    } catch (e) {
      console.log(e);
    }
  };

  const getHost = async () => {
    try {
      const result = await checkWriter(MoimId);
      setIsHost(result.data.isHost);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getHost();
  }, []);

  return (
    <>
      <div className="flex justify-center items-center self-start font-Pretendard_Black font-normal text-black max-md:px-5 max-md:max-w-full">
        <div className="flex gap-12 font-bold text-3xl">
          <div
            className={`justify-center max-md:px-5 cursor-pointer ${
              selected === homeKey
                ? "text-scarlet border-b-4 pb-2 border-scarlet"
                : ""
            }`}
            onClick={() => setSelected(homeKey)}
          >
            {homeKey}
          </div>
          <div
            className={`justify-center max-md:px-5 cursor-pointer ${
              selected === availableTimeKey
                ? "text-scarlet  border-b-4 pb-2 border-scarlet"
                : ""
            }`}
            onClick={() => setSelected(availableTimeKey)}
          >
            {availableTimeKey}
          </div>
          <div
            className={`justify-center max-md:px-5 cursor-pointer ${
              selected === todoKey
                ? "text-scarlet  border-b-4 pb-2 border-scarlet"
                : ""
            }`}
            onClick={() => setSelected(todoKey)}
          >
            {todoKey}
          </div>
          <div
            className={`justify-center max-md:px-5 cursor-pointer ${
              selected === memberKey
                ? "text-scarlet  border-b-4 pb-2 border-scarlet"
                : ""
            }`}
            onClick={() => setSelected(memberKey)}
          >
            {memberKey}
          </div>
        </div>
      </div>
      {selected === homeKey ? (
        <MoimHome meetings={meetings} isHost={isHost} moimId={MoimId} />
      ) : selected === availableTimeKey ? (
        <AvailableTime moimId={MoimId} />
      ) : selected === todoKey ? (
        <ToDo />
      ) : selected === memberKey ? (
        <Member isHost={isHost} MoimId={MoimId} />
      ) : null}
      <Modal
        show={isAlertModalOpen}
        size="sm"
        onClose={() => setAlertModalOpen(false)}
        theme={modalTheme}
        popup
      >
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
              {message}
            </h3>
            <div className="flex justify-center gap-4">
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={() => {
                  setAlertModalOpen(false);
                }}
              >
                확인
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default MoimJoinPage;
