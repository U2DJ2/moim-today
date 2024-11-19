// React
import { useState, useEffect } from "react";

// UI


// Components
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import AlertModal from "../../components/Modal/index.jsx";
import ModalTheme from "../../components/Modal/modalTheme.js";


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
      <AlertModal show={openAlertModal} onClose={()=>setOpenAlertModal(false)} message={"이메일 혹은 비밀번호를 다시 입력해주세요."} theme={ModalTheme}/>
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
