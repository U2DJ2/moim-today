import React from "react";
import AuthRight from "../../components/AuthRight";
import Account from "./Account";
import { useState } from "react";
import { useForm } from "react-hook-form";
import AuthCheck from "./AuthCheck";
function RegisterPage() {
  const [step, setStep] = useState(0);
  const [activeNext, setActiveNext] = useState(false);
  const { register, handleSubmit, errors, formState, setValue } = useForm();
  return (
    <div className="flex min-h-screen w-full py-0 overflow-hidden relative gap-1 pl-28 bg-scarlet">
      {step === 0 ? (
        <Account register={register} errors={errors} setValue={setValue} />
      ) : step === 1 ? (
        <AuthCheck register={register} errors={errors} setValue={setValue} />
      ) : null}

      <AuthRight textColor={"scarlet"} cardColor={"white"} />
    </div>
  );
}

export default RegisterPage;
