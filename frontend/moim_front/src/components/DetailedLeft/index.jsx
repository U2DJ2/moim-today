// React
import { useState } from "react";
import { useNavigate, useParams } from "react-router";

// API
import axios from "axios";

// UI
import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";

// Images
import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";

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

function DetailedLeft({
  userName,
  title,
  currentCount,
  capacity,
  joined,
  image,
  setMessage,
  setIsOpen,
}) {
  let { MoimId } = useParams();
  console.log(MoimId);

  const navigate = useNavigate();
  const [isAlertModalOpen, setAlertModalOpen] = useState(false);

  const body = {
    moimId: MoimId,
  };

  const onClickHandler = () => {
    axios
      .post("https://api.moim.today/api/moims/members", body)
      .then(() => {
        setAlertModalOpen(true);
      })
      .catch((error) => {
        setIsOpen(true);
        setMessage(error.response.data.message);
      });
  };

  return (
    <>
      <Modal show={isAlertModalOpen} size="sm" onClose={() => setAlertModalOpen(false)} theme={modalTheme} popup>
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
              {"모임에 가입되셨습니다!"}
            </h3>
            <div className="flex justify-center gap-4">
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={() => {
                  setAlertModalOpen(false);
                  navigate(`/join/${MoimId}`);
                }}
              >
                헐랭...
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
      <div className="flex flex-col basis-1/5 gap-4 items-center h-full min-h-screen md:basis-1/6">
        <img className=" w-72 h-60 rounded-t-2xl" src={image} />
        <div className=" font-Pretendard_Normal ">{userName}</div>
        <div className="text-center font-Pretendard_Black text-3xl text-[#3F3F3F]">
          {title}
        </div>
        <div className="flex gap-1 font-Pretendard_SemiBold text-sm text-[#6F6F6F] hover:cursor-pointer hover:text-scarlet">
          <img src={icon} />
          <div className="flex">
            <div>{currentCount}</div>
            <div>/</div>
            <div>{capacity}</div>
          </div>
        </div>
        <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F] hover:cursor-pointer hover:text-scarlet">
          <img src={clock} />
          <span className="ml-2">가용시간 보기</span>
        </div>
        <button
          className={`${joined ? "hidden" : "flex"
            } w-52 justify-center px-14 py-4 text-xl text-center text-white bg-black whitespace-nowrap rounded-[50px] font-Pretendard_Normal hover:cursor-pointer hover:bg-scarlet`}
          onClick={onClickHandler}
        >
          🏳️ 참여하기
        </button>
      </div>
<<<<<<< HEAD

      <button
        className={`${
          joined ? "hidden" : "flex"
        } w-52 justify-center px-14 py-4 text-xl text-center text-white bg-black whitespace-nowrap rounded-[50px] font-Pretendard_Normal hover:cursor-pointer hover:bg-scarlet`}
        onClick={onClickHandler}
      >
        🏳️ 참여하기
      </button>
    </div>
=======
    </>
>>>>>>> frontend-main
  );
}

export default DetailedLeft;
