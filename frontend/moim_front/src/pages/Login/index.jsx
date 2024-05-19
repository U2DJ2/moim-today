// React
import { useState } from "react";

// Components
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import Modal from "../../components/Modal/ModalTest";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState("");

  const modalHandler = () => {
    setIsOpen(!isOpen);
  };
  return (
    <div className="flex h-screen w-full overflow-hidden relative gap-12">
      <AuthLeft
        className="flex flex-col"
        title={"로그인"}
        firstContent={"환영합니다!"}
        secondContent={"모임 투데이 계정으로 로그인하세요."}
        titleColor={"black"}
        contentColor={"black"}
        email={email}
        setEmail={setEmail}
        password={password}
        setPassword={setPassword}
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        message={message}
        setMessage={setMessage}
      />
      <AuthRight textColor={"white"} cardColor={"scarlet"} />
      <Modal
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        closeModal={modalHandler}
        message={message}
      />
    </div>
  );
}

export default LoginPage;
