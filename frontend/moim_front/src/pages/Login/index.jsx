// React
import { useState } from "react";

//UI
import ModalTheme from "../../components/Modal/modalTheme.js";

// Components
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import AlertModal from "../../components/Modal/index.jsx";

// hook
import useResize from "../../hook/useResize.js";


function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [openAlertModal, setOpenAlertModal] = useState(false);

  const isSmallScreen = useResize(1280);

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
