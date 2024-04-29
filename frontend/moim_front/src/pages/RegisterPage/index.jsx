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
function RegisterPage() {
  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);
  const { register, handleSubmit, errors, formState, setValue } = useForm();
  const navigate = useNavigate();
  const nextClick = async () => {
    setStep(step + 1);
    setActiveNext(false);
  };
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28 bg-scarlet">
      <div className="flex flex-1  flex-col items-start justify-center w-96 gap-16">
        {step === 0 ? (
          <Account
            register={register}
            errors={errors}
            setValue={setValue}
            setActiveNext={setActiveNext}
          />
        ) : step === 1 ? (
          <AuthCheck register={register} errors={errors} setValue={setValue} />
        ) : step === 2 ? (
          <School register={register} errors={errors} setValue={setValue} />
        ) : step === 3 ? (
          <Personal register={register} errors={errors} setValue={setValue} />
        ) : null}
        {step !== 4 && (
          <div className="flex justify-center mt-16">
            <div className="flex gap-8">
              <Button
                name={"취소"}
                textColor={"gray"}
                bgColor={"white"}
                btnType={"cancel"}
                onClick={() => navigate(-1)}
              />
              <Button
                name={"다음"}
                textColor={"[#646464]"}
                bgColor={"white"}
                btnType={"next"}
                onClick={() => nextClick()}
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
