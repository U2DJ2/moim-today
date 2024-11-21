// React
import { useState } from "react";

//UI

// Components
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import AlertModal from "../../components/AlertModal/index.jsx";

// hook
import useResize from "../../hook/useResize.js";
import useModal from "../../hook/useModal.js";


function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const isSmallScreen = useResize(1280);
    const {setShowModal, openModal, showModal, closeModal, message} = useModal();

    return (
    <>
      <AlertModal show={showModal} onClose={closeModal} message={message} />
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
          setShowModal = {setShowModal}
          setOpenAlertModal={openModal}
          className={`${isSmallScreen ? '' : 'mt-6'} flex flex-col w-fit justify-center items-center overflow-hidden sm:overflow-visible`}
        />
        {!isSmallScreen && <AuthRight textColor={"white"} cardColor={"scarlet"} />}
      </div>
    </>
  );
}

export default LoginPage;
