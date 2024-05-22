// React
import { useState, useEffect } from "react";

// UI
import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";

// Components
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";

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

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [isSmallScreen, setIsSmallScreen] = useState(false);
  const [openAlertModal, setOpenAlertModal] = useState(false);

  // 화면 크기가 작은지 여부를 판단하는 함수
  const handleResize = () => {
    setIsSmallScreen(window.innerWidth <= 1280); // 작은 화면 기준 (예: 모바일 화면)
  };

  // 컴포넌트가 마운트될 때와 화면 크기가 변경될 때 이벤트 리스너 등록
  useEffect(() => {
    window.addEventListener("resize", handleResize);
    handleResize(); // 초기 화면 크기 설정
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <>
      <Modal show={openAlertModal} size="lg" onClose={() => setOpenAlertModal(false)} theme={modalTheme} popup>
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
                onClick={() => setOpenAlertModal(false)}
              >
                헉! 오또케
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>

      <div className={`flex h-screen overflow-hidden relative gap-24 ${isSmallScreen ? 'justify-center items-center' : 'ml-36'}`}>
        <AuthLeft
          title={"로그인"}
          firstContent={"환영합니다!"}
          secondContent={"모임 투데이 계정으로 로그인하세요."}
          titleColor={"black"}
          contentColor={"black"}
          email={email}
          setEmail={setEmail}
          password={password}
          setPassword={setPassword}
          setOpenAlertModal={setOpenAlertModal}
          message={message}
          setMessage={setMessage}
          className={`${isSmallScreen ? '' : 'mt-6'} flex flex-col w-fit justify-center items-center overflow-hidden sm:overflow-visible`}
        />
        {!isSmallScreen && <AuthRight textColor={"white"} cardColor={"scarlet"} />}
      </div>
    </>
  );
}

export default LoginPage;