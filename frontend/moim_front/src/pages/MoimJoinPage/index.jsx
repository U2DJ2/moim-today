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

const modalTheme = {
  "root": {
    "base": "fixed inset-x-0 top-0 z-50 h-screen overflow-y-auto overflow-x-hidden md:inset-0 md:h-full",
    "show": {
      "on": "flex bg-gray-900 bg-opacity-50 dark:bg-opacity-80",
      "off": "hidden"
    },
    "sizes": {
      "sm": "max-w-sm",
      "md": "max-w-md",
      "lg": "max-w-lg",
      "xl": "max-w-xl",
      "2xl": "max-w-2xl",
      "3xl": "max-w-3xl",
      "4xl": "max-w-4xl",
      "5xl": "max-w-5xl",
      "6xl": "max-w-6xl",
      "7xl": "max-w-7xl"
    },
    "positions": {
      "top-left": "items-start justify-start",
      "top-center": "items-start justify-center",
      "top-right": "items-start justify-end",
      "center-left": "items-center justify-start",
      "center": "items-center justify-center",
      "center-right": "items-center justify-end",
      "bottom-right": "items-end justify-end",
      "bottom-center": "items-end justify-center",
      "bottom-left": "items-end justify-start"
    }
  },
  "content": {
    "base": "relative h-full w-full p-4 md:h-auto",
    "inner": "relative flex max-h-[90dvh] flex-col rounded-xl bg-white shadow dark:bg-gray-700"
  },
  "body": {
    "base": "flex-1 overflow-auto p-6",
    "popup": "pt-0"
  },
  "header": {
    "base": "flex items-start justify-between rounded-t border-b mt-0.5 p-5 dark:border-gray-600",
    "popup": "border-b-0 p-2",
    "title": "pt-0.5 text-xl font-Pretendard_SemiBold text-gray-900 dark:text-white",
    "close": {
      "base": "ml-auto inline-flex items-center rounded-lg bg-transparent p-1.5 text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900 dark:hover:bg-gray-600 dark:hover:text-white",
      "icon": "h-5 w-5"
    }
  },
  "footer": {
    "base": "flex items-center space-x-2 rounded-b border-gray-200 p-6 dark:border-gray-600",
    "popup": "border-t"
  }
}

function MoimJoinPage() {
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [selected, setSelected] = useState("HOME");

  const [meetingOption, setMeetingOption] = useState("ALL");

  const [notices, setNotices] = useState([]);
  const [moimInfo, setMoimInfo] = useState([]);
  const [meetings, setMeetings] = useState([]);

  const [writerInfo, setWriterInfo] = useState([]);

  const [isHost, setIsHost] = useState(false);
  const { MoimId } = useParams();

  const getNotices = async () => {
    try {
      const result = await fetchNotices(MoimId);
      console.log(result);
      setNotices(result.data.data);
    } catch (e) {
      console.log(e);
    }
  };

  const getInfo = async () => {
    try {
      const result = await fetchMoimInfo(MoimId);
      setMoimInfo(result.data);
    } catch (e) {
      console.log(e);
    }
  };

  const getMeetings = async () => {
    try {
      const result = await fetchMeetings(MoimId, meetingOption);
      console.log(result.data.data);

      setMeetings(result.data.data);
    } catch (e) {
      console.log(e);
    }
  };
  const fetchWriter = async () => {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/members/host-profile/${MoimId}`
      );
      console.log(response.data);
      setWriterInfo(response.data);
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
    getNotices();
    getMeetings();
    getInfo();
    fetchWriter();
  }, []);
  return (
    <MoimContainer>
      <DetailedLeft
        userName={writerInfo.username}
        title={moimInfo.title}
        currentCount={moimInfo.currentCount}
        capacity={moimInfo.capacity}
        category={moimInfo.category}
        contents={moimInfo.contents}
        image={moimInfo.imageUrl}
        joined={true}
      />
      <div className="flex flex-col basis-4/5 bg-white shadow-lg overflow-hidden rounded-t-3xl px-20 pt-16 pb-6 gap-8 h-full">
        <div className="flex justify-center items-center self-start font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-12 font-semibold font-Roboto lg:text-xl lg:gap-8 xl:text-3xl 2xl:text-4xl">
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${selected === "HOME" ? "text-scarlet border-b-2 border-scarlet" : "" }`}
              onClick={() => setSelected("HOME")}
            >
              HOME
            </div>
            <div
              className={`justify-center  max-md:px-5 cursor-pointer ${selected === "되는시간" ? "text-scarlet border-b-2 border-scarlet" : ""}`}
              onClick={() => setSelected("되는시간")}
            >
              되는시간
            </div>
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${selected === "ToDo" ? "text-scarlet border-b-2 border-scarlet" : "" }`}
              onClick={() => setSelected("ToDo")}
            >
              ToDo
            </div>
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${selected === "멤버" ? "text-scarlet border-b-2 border-scarlet" : "" }`}
              onClick={() => setSelected("멤버")}
            >
              멤버
            </div>
          </div>
        </div>
        {selected === "HOME" ? (
          <MoimHome
            notices={notices}
            meetings={meetings}
            meetingOption={meetingOption}
            setMeetingOption={setMeetingOption}
            isHost={isHost}
            moimId={MoimId}
          />
        ) : selected === "되는시간" ? (
          <AvailableTime />
        ) : selected === "ToDo" ? (
          <ToDo />
        ) : selected === "멤버" ? (
          <Member isHost={isHost} MoimId={MoimId} />
        ) : null}
      </div>
      <Modal show={isOpen} size="sm" onClose={() => setIsOpen(false)} theme={modalTheme} popup>
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
                  setIsOpen(false);
                }}
              >
                확인
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </MoimContainer>
  );
}

export default MoimJoinPage;
