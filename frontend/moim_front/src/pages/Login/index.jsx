import { useState } from "react";
import AuthRight from "../../components/AuthRight";
import AuthLeft from "../../components/AuthLeft";
import right1 from "../../assets/svg/right1.svg";
import { POST } from "../../utils/axios";
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
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-52">
      <AuthLeft
        className="flex flex-col w-full"
        title={"Login"}
        firstContent={"Welcome back!"}
        secondContent={"Please login to your account."}
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
