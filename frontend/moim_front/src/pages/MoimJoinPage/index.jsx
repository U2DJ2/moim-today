import React, { useState } from "react";
import MoimContainer from "../../components/PageContainer/MoimContainer";
import DetailedLeft from "../../components/DetailedLeft";
import MoimHome from "./MoimHome";
import AvailableTime from "./AvailableTime";
import ToDo from "./ToDo";
import Member from "./Member";

function MoimJoinPage() {
  const [selected, setSelected] = useState("HOME");

  return (
    <div className="flex w-screen min-h-full screen bg-gradient-to-b from-white to-[#F6F8FE] justify-start gap-9 px-24">
      <DetailedLeft
        userName={"김유림"}
        title={"컴구 스터디 구합니다"}
        currentCount={7}
        capacity={10}
        category={"스터디"}
        contents={
          "안녕하세요! 아주대학교 소프트웨어학과 20학번 김유림입니다. 컴퓨터 구조 공부를 하고 있는데, 혼자하니까 의지박약으로 쉽지 않네요...함께 열심히 공부하실 분 구합니다!"
        }
        joined={true}
      />
      <div className="flex flex-col basis-4/5 bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] rounded-3xl px-24 gap-16 h-5/6 pb-16">
        <div className="flex justify-center items-center self-start px-16 font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-3">
            <div
              className={`justify-center px-9 py-3 rounded-[64px] text-xl max-md:px-5 cursor-pointer ${
                selected === "HOME" ? "bg-gray-200" : ""
              }`}
              onClick={() => setSelected("HOME")}
            >
              HOME
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] text-xl max-md:px-5 cursor-pointer ${
                selected === "되는시간" ? "bg-gray-200" : ""
              }`}
              onClick={() => setSelected("되는시간")}
            >
              되는시간
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] text-xl max-md:px-5 cursor-pointer ${
                selected === "ToDo" ? "bg-gray-200" : ""
              }`}
              onClick={() => setSelected("ToDo")}
            >
              ToDo
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] text-xl max-md:px-5 cursor-pointer ${
                selected === "멤버" ? "bg-gray-200" : ""
              }`}
              onClick={() => setSelected("멤버")}
            >
              멤버
            </div>
          </div>
        </div>
        {selected === "HOME" ? (
          <MoimHome />
        ) : selected === "되는시간" ? (
          <AvailableTime />
        ) : selected === "ToDo" ? (
          <ToDo />
        ) : selected === "멤버" ? (
          <Member />
        ) : null}
      </div>
    </div>
  );
}

export default MoimJoinPage;
