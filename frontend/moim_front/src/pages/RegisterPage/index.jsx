import React from "react";
import AuthRight from "../../components/AuthRight";
import Account from "./Account";
import { useState } from "react";
import AuthCheck from "./AuthCheck";
import { checkEmailValid } from "../../api/users.js";
import { useNavigate } from "react-router";
import School from "./School";
import Personal from "./Personal";
import TimeTable from "./TimeTable";
import Congrats from "./Congrats";
import axios from "axios";
import Modal from "../../components/Modal/ModalTest.jsx";
import ConfirmModal from "./ConfirmModal/index.jsx";

function RegisterPage() {
  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);

  const [universityName, setUniversityName] = useState("");
  const [emailDuplication, setEmailDuplication] = useState(false);
  const [emailValidation, setEmailValidation] = useState(false);

  const [schoolValidation, setSchoolValidation] = useState(false);
  const [emailAuth, setEmailAuth] = useState(false);
  const [department, setDepartment] = useState("");
  /* register form data */
  const [universityId, setUniversityId] = useState();
  const [departmentId, setDepartmentId] = useState();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUserName] = useState("");
  const [studentId, setStudentId] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [gender, setGender] = useState("");
  const [message, setMessage] = useState("");

  const [isOpen, setIsOpen] = useState(false);
  const client = axios.create({
    baseURL: "https://api.moim.today/",
  });
  const emailBody = {
    email: email,
  };
  const userData = {
    universityId: universityId,
    departmentId: departmentId,
    email: email,
    password: password,
    username: username,
    studentId: studentId,
    birthDate: birthDate,
    gender: gender,
  };
  const nextClick = async () => {
    if (step === 0) {
      //check email validity
      client.post("api/certification/email", emailBody).then((res) => {
        if (res.statusCode === 400) {
          setEmailDuplication(true);
          isOpen(true);
          console.log(res);
          message("이미 있는 이메일 입니다");
        } else if (res.statusCode === 404) return setSchoolValidation(true);
        setStep(step + 1);
        console.log(res);
      });
    } else if (step === 1) {
      client.post("api/certification/email/complete", emailBody).then((res) => {
        if (res.status === "400") {
          console.log(res.status);
          setEmailValidation(true);
          setIsOpen(true);
        }
        console.log(res);
        console.log(res.status);
        setUniversityName(res.data.universityName);
        setUniversityId(res.data.universityId);
        console.log(setUniversityId);
        setStep(step + 1);
      });
    } else if (step === 3) {
      client.post("api/sign-up", userData).then((res) => {
        if (res.status === "404") {
          isOpen(true);
          message(
            "이메일 또는 비밀번호가 올바르지 않습니다. 다시 확인해주세요."
          );
        }
        console.log(res.data);
        setStep(step + 1);
      });
    } else {
      setStep(step + 1);
      setActiveNext(false);
    }
  };
  const previousClick = () => {
    if (step === 0) navigate(-1);
    else setStep(step - 1);
  };
  const handleModal = () => {
    setIsOpen(false);
  };
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-52 bg-scarlet">
      <div className="flex flex-1 flex-col items-start justify-center w-96 gap-16">
        {step === 0 ? (
          <Account
            email={email}
            setEmail={setEmail}
            password={password}
            setPassword={setPassword}
            setActiveNext={setActiveNext}
          />
        ) : step === 1 ? (
          <AuthCheck
            setEmailAuth={setEmailAuth}
            setActiveNext={setActiveNext}
          />
        ) : step === 2 ? (
          <School
            universityId={universityId}
            universityName={universityName}
            studentId={studentId}
            setStudentId={setStudentId}
            department={department}
            setDepartment={setDepartment}
            setActiveNext={setActiveNext}
            setDepartmentId={setDepartmentId}
            departmentId={departmentId}
          />
        ) : step === 3 ? (
          <Personal
            username={username}
            setUserName={setUserName}
            birthDate={birthDate}
            setBirthDate={setBirthDate}
            gender={gender}
            setGender={setGender}
            setActiveNext={setActiveNext}
          />
        ) : step === 4 ? (
          <Congrats />
        ) : (
          <TimeTable />
        )}

        {step !== 4 && (
          <div className="flex justify-center mt-16">
            <div className="flex gap-8">
              <button
                className={`w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-black bg-white whitespace-nowrap rounded-[50px] font-Pretendard_Black hover:cursor-pointer  `}
                onClick={() => previousClick()}
              >
                이전
              </button>
              <button
                className={`${
                  activeNext
                    ? "w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-scarlet bg-white whitespace-nowrap rounded-[50px] font-Pretendard_Black hover:cursor-pointer "
                    : "w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-[#8D8D8D] bg-white whitespace-nowrap rounded-[50px] font-Pretendard_Black hover:cursor-pointer "
                }`}
                disabled={!activeNext}
                onClick={() => nextClick()}
              >
                다음
              </button>
            </div>
          </div>
        )}
      </div>
      <AuthRight cardColor={"white"} textColor={"scarlet"} />
      <Modal isOpen={isOpen} closeModal={handleModal} message={message} />
    </div>
  );
}

export default RegisterPage;
