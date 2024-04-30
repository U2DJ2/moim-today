import React from "react";
import AuthRight from "../../components/AuthRight";
import Account from "./Account";
import { useState } from "react";
import { useForm } from "react-hook-form";
import AuthCheck from "./AuthCheck";
import Button from "../../components/Button";
import { checkEmailValid } from "../../api/users";
import { useNavigate } from "react-router";
import School from "./School";
import Personal from "./Personal";
import TimeTable from "./TimeTable";
import Congrats from "./Congrats";

function RegisterPage() {
  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const nextBtnHandler = (data) => {
    console.log(data);
  };
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
    watch,
  } = useForm();
  const navigate = useNavigate();
  const nextClick = (data) => {
    setStep(step + 1);
    setActiveNext(false);

    console.log(data);
    // if (step === 1) {
    //   //check email validity
    //   // checkEmailValid(data.email).then(() => {
    //   // });
    // } else {
    //   setStep(step + 1);
    //   setActiveNext(false);
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
            register={register}
            errors={errors}
            setValue={setValue}
            setActiveNext={setActiveNext}
            onSubmit={handleSubmit(nextClick)}
          />
        ) : step === 1 ? (
          <AuthCheck register={register} errors={errors} setValue={setValue} />
        ) : step === 2 ? (
          <School register={register} errors={errors} setValue={setValue} />
        ) : step === 3 ? (
          <Personal register={register} errors={errors} setValue={setValue} />
        ) : step === 4 ? (
          <TimeTable register={register} errors={errors} setValue={setValue} />
        ) : step === 5 ? (
          <Congrats register={register} errors={errors} setValue={setValue} />
        ) : null}

        {step !== 5 && (
          <div className="flex justify-center mt-16">
            <div className="flex gap-8">
              <Button
                name={"취소"}
                textColor={"gray"}
                bgColor={"white"}
                btnType={"cancel"}
                onClick={previousClick}
              />
              <Button
                name={"다음"}
                textColor={"[#646464]"}
                bgColor={"white"}
                btnType={"next"}
                onClick={nextClick}
              />
            </div>
          </div>
        )}
      </div>
      <AuthRight cardColor={"white"} textColor={"scarlet"} />
    </div>
  );
}

export default RegisterPage;
