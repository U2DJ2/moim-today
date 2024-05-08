import React from "react";
import Header from "../../components/Header";
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";

function MoimDetailPage() {
  return (
    <div className="bg-gradient-to-b from-white to-[#F6F8FE] min-h-screen ">
      <div className="grid grid-flow-col gap-9 px-24 min-h-screen">
        <DetailedLeft
          userName={"김유림"}
          title={"컴구 스터디 구합니다"}
          currentCount={7}
          capacity={10}
          joined={false}
        />
        <DetailedRight
          category={"스터디"}
          title={"컴구 스터디 구합니다"}
          currentCount={7}
          capacity={10}
          contents={
            "안녕하세요! 아주대학교 소프트웨어학과 20학번 김유림입니다. 컴퓨터 구조 공부를 하고 있는데, 혼자하니까 의지박약으로 쉽지 않네요...함께 열심히 공부하실 분 구합니다!"
          }
          className={"pl-3"}
        />
      </div>
    </div>
  );
}

export default MoimDetailPage;
