import React, { useState, useEffect } from "react";
import AuthTitle from "../../../components/Authentification/AuthTitle";

function Personal({
  username,
  setUserName,
  birthDate,
  setBirthDate,
  gender,
  setGender,
  setActiveNext,
}) {
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  // const [optionVisible, setOptionVisible] = useState();
  const checkInputsFilled = (username, year, month, day, gender) => {
    if (
      username.trim() !== "" &&
      year.trim() !== "" &&
      month.trim() !== "" &&
      day.trim() !== "" &&
      gender.trim() !== ""
    ) {
      setBirthDate(`${year}-${month}-${day}`);
      setActiveNext(true);
    } else setActiveNext(false);
  };
  const usernameHandler = (e) => {
    setUserName(e.target.value);
    checkInputsFilled(e.target.value, year, month, day, gender);
  };
  const handleChangeYear = (e) => {
    setYear(e.target.value);
    checkInputsFilled(username, e.target.value, month, day, gender);
  };
  const handleChangeMonth = (e) => {
    setMonth(e.target.value);
    checkInputsFilled(e.target.value, year, month, day, gender);
  };
  const handleChangeDay = (e) => {
    setDay(e.target.value);
    checkInputsFilled(e.target.value, year, month, day, gender);
  };
  useEffect(() => {
    checkInputsFilled(username, year, month, day, gender);
  }, [username, year, month, day, gender]);

  return (
    <div className="flex flex-col gap-16">
      <AuthTitle
        title={"Personal"}
        firstContent={"이제 세번째 단계입니다."}
        secondContent={"이름과 생일, 성별을 입력해주세요!"}
        titleColor={"white"}
        contentColor={"white"}
      />
      <div className="flex flex-col gap-8">
        <div>
          <p className="font-Pretendard_Black block text-xl text-white">이름</p>
          <input
            type="text"
            name="username"
            autoComplete="off"
            placeholder="enter your name"
            className={`border-b border-white font-Pretendard_Light text-white text-xl pt-2 pb-2 bg-scarlet focus:outline-none w-full block placeholder:text-white`}
            value={username}
            onChange={usernameHandler}
          />
        </div>
        <div className="flex flex-col gap-3">
          <p className=" font-Pretendard_Black block text-xl text-white">
            생년월일
          </p>
          <div className="flex gap-4 ">
            <input
              type="number"
              className="border text-wrapper border-white text-white bg-scarlet placeholder:text-white placeholder:justify-items-center justify-items-center
            text-xl rounded-lg px-4 py-1 focus:outline-none w-20"
              placeholder="YYYY"
              onChange={handleChangeYear}
            />
            <input
              type="number"
              className="border text-wrapper border-white text-white bg-scarlet placeholder:text-white 
         text-xl rounded-lg px-4 py-1 w-20 focus:outline-none placeholder:text-center justify-items-center"
              placeholder="MM"
              onChange={handleChangeMonth}
            />
            <input
              type="number"
              className="border text-wrapper border-white text-white bg-scarlet placeholder:text-white 
        text-xl rounded-lg px-4 py-1 w-20 focus:outline-none placeholder:text-center"
              placeholder="DD"
              onChange={handleChangeDay}
            />
          </div>
        </div>
        <div className="flex flex-col gap-3">
          <p className=" font-Pretendard_Black block text-xl text-white">
            성별
          </p>
          <div className="flex gap-4">
            <button
              className={`${
                gender === "MALE"
                  ? "border border-white bg-white text-scarlet placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none font-bold"
                  : "border border-white bg-scarlet text-white placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none"
              }`}
              onClick={() => setGender("MALE")}
            >
              남성
            </button>
            <button
              className={`${
                gender === "FEMALE"
                  ? "border border-white bg-white text-scarlet placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none font-bold"
                  : "border border-white bg-scarlet text-white placeholder:w-fit font-Pretendard_Normal text-xl rounded-lg px-4 py-1 focus:outline-none "
              }`}
              onClick={() => setGender("FEMALE")}
            >
              여성
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Personal;
