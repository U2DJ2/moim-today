// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";

// API
import axios from "axios";

// Components
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";

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

function MoimDetailPage() {
  const { MoimId } = useParams();
  const [isAlertOpen, setAlertOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [moimInfo, setMoimInfo] = useState([]);
  const [writerInfo, setWriterInfo] = useState([]);

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

  const fetchMoimDetail = async () => {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/moims/detail/${MoimId}`
      );
      setMoimInfo(response.data);
    } catch (error) {
      setMessage(error.response.data.message);
    }
  };

  useEffect(() => {
    fetchWriter();
    fetchMoimDetail();

    // eslint-disable-next-line
  }, []);

  return (
    <div className="bg-gradient-to-b justify-center from-white to-[#F6F8FE] pl-10">
        <div className="flex gap-8 pt-2 flex-1 overflow-auto">
          <DetailedLeft
            userName={writerInfo.username}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            joined={false}
            image={moimInfo.imageUrl}
            setMessage={setMessage}
            setIsOpen={setAlertOpen}
          />
          <DetailedRight
            category={moimInfo.category}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            contents={moimInfo.contents}
            className={"pl-3"}
          />
        </div>
      <Modal show={isAlertOpen} size="sm" onClose={() => setAlertOpen(false)} theme={modalTheme} popup>
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
                  setAlertOpen(false);
                }}
              >
                확인
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </div>
  );
}

export default MoimDetailPage;