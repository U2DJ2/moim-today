import React, { useState } from "react";
import MoimContainer from "../../components/PageContainer/MoimContainer";
import DetailedLeft from "../../components/DetailedLeft";
import MoimHome from "./MoimHome";
import AvailableTime from "./AvailableTime";
import ToDo from "./ToDo";
import Member from "./Member";

function MoimJoinPage() {
  const [selected, setSelected] = useState("HOME");
  const [step, setStep] = useState(0);
  const tapClick = async () => {
    if (selected === "HOME") {
    }
  };
  return (
    //     <div className="w-full relative h-[808px] flex flex-row items-center justify-start py-0 px-9 box-border gap-[36px] text-center text-[32px] text-darkslategray font-headline-h5">
    //       <div className="self-stretch w-[315px] flex flex-col items-center justify-start pt-0 px-0 pb-2.5 box-border">
    //         <div className="self-stretch flex flex-col items-center justify-start">
    //           <img
    //             className="self-stretch rounded-t-xl rounded-b-none max-w-full overflow-hidden h-[241px] shrink-0 object-cover"
    //             alt=""
    //             src="card-image.png"
    //           />
    //         </div>
    //         <div className="self-stretch flex flex-col items-center justify-start gap-[20px]">
    //           <div className="w-56 h-[60px]" />
    //         </div>
    //       </div>
    //       <div className="w-[939px] relative shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] rounded-[30px] bg-white h-[808px] overflow-hidden shrink-0 text-left text-21xl text-black">
    //         <div className="absolute top-[50px] left-[34px] flex flex-row items-start justify-start text-center font-roboto-flex">
    //           <div className="flex flex-row items-center justify-center gap-[40px]">
    //             <div className="flex flex-row items-center justify-center py-0 px-3.5">
    //               <div className="relative font-semibold">HOME</div>
    //             </div>
    //             <div className="flex flex-row items-center justify-center py-0 px-1">
    //               <div className="relative font-semibold">되는시간</div>
    //             </div>
    //             <div className="flex flex-col items-center justify-center py-1 px-0 text-crimson">
    //               <div className="relative font-semibold">TODO</div>
    //               <div className="w-[82px] relative box-border h-0.5 border-t-[2px] border-solid border-crimson" />
    //             </div>
    //             <div className="flex flex-row items-center justify-center py-0 px-[27px]">
    //               <div className="relative font-semibold">멤버</div>
    //             </div>
    //           </div>
    //         </div>
    //       </div>
    //     </div>
    //   );
    // }

    <div className="w-full bg-gradient-to-b from-white to-[#F6F8FE] grid grid-flow-col justify-start gap-9 px-24 min-h-screen">
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
      <div className="flex flex-col flex-1 bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] w-full rounded-3xl px-24 gap-16 h-full">
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
