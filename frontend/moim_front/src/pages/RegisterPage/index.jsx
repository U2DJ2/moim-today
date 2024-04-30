import React from "react";
import AuthRight from "../../components/AuthRight";
import Account from "./Account";
import { useState } from "react";
import AuthCheck from "./AuthCheck";

import { useNavigate } from "react-router";
import School from "./School";
import Personal from "./Personal";
import TimeTable from "./TimeTable";
import Congrats from "./Congrats";

function RegisterPage() {
  const navigate = useNavigate();

  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailDuplication, setEmailDuplication] = useState(false);
  const [schoolValidation, setSchoolValidation] = useState(false);
  const [emailAuth, setEmailAuth] = useState(false);
  const [schoolName, setSchoolName] = useState("");
  const [department, setDepartment] = useState("");
  const [studentId, setStudentId] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [gender, setGender] = useState("");

  const nextClick = async () => {
    // if (step === 0) {
    //   //check email validity
    //   checkEmailValid("email", email).then((res) => {
    //     if (res.statusCode === 200) return setEmailDuplication(true);
    //     else if (res.statusCode === 404) return setSchoolValidation(false);
    //   });
    // } else {
    setStep(step + 1);
    setActiveNext(false);
    // }
  };
  const previousClick = () => {
    if (step === 0) navigate(-1);
    else setStep(step - 1);
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
            emailAuth={emailAuth}
            setEmailAuth={setEmailAuth}
            setActiveNext={setActiveNext}
          />
        ) : step === 2 ? (
          <School
            schoolName={schoolName}
            setSchoolName={setSchoolName}
            studentId={studentId}
            setStudentId={setStudentId}
            department={department}
            setDepartment={setDepartment}
            setActiveNext={setActiveNext}
          />
        ) : step === 3 ? (
          <Personal
            birthDate={birthDate}
            setBirthDate={setBirthDate}
            gender={gender}
            setGender={setGender}
            setActiveNext={setActiveNext}
          />
        ) : step === 4 ? (
          <TimeTable />
        ) : (
          <Congrats />
        )}

        {step !== 5 && (
          <div className="flex justify-center mt-16">
            <div className="flex gap-8">
              <button
                className={`w-52 justify-center px-7 py-5 text-[22px] font-bold text-center text-black bg-white whitespace-nowrap rounded-[50px] font-Pretendard_Black hover:cursor-pointer  `}
                onClick={() => previousClick}
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
    </div>
  );
}

export default RegisterPage;
