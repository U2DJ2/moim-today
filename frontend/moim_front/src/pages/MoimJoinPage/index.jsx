import React, { useState, useEffect } from "react";
import MoimContainer from "../../components/PageContainer/MoimContainer";
import DetailedLeft from "../../components/DetailedLeft";
import MoimHome from "./MoimHome";
import AvailableTime from "./AvailableTime";
import ToDo from "./ToDo";
import Member from "./Member";
import Modal from "../../components/Modal/ModalTest";
import axios from "axios";
import { useParams } from "react-router";

function MoimJoinPage() {
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [selected, setSelected] = useState("HOME");

  const [notices, setNotices] = useState([]);
  const { MoimId } = useParams();

  const params = {
    moimId: MoimId,
  };
  useEffect(() => {
    const fetchJoinInfo = async () => {
      const response = await axios.get(
        "https://api.moim.today/api/moims/notices/simple",
        {
          params: params,
        }
      );
      //console.log(response.data.data);
      setNotices(response.data.data);
      try {
      } catch (error) {
        setIsOpen(true);
        setMessage(error.response);
        console.log(error);
      }
    };
    fetchJoinInfo();
  }, []);

  return (
    <MoimContainer>
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
      <div className="flex flex-col basis-4/5 bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] overflow-hidden rounded-3xl px-24 pb-6 gap-8 min-h-[600px] h-fit">
        <div className="flex justify-center items-center self-start font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-12 font-semibold font-Roboto lg:text-xl lg:gap-8 xl:text-3xl 2xl:text-4xl">
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${
                selected === "HOME"
                  ? "text-scarlet border-b-2 border-scarlet"
                  : ""
              }`}
              onClick={() => setSelected("HOME")}
            >
              HOME
            </div>
            <div
              className={`justify-center  max-md:px-5 cursor-pointer ${
                selected === "되는시간"
                  ? "text-scarlet border-b-2 border-scarlet"
                  : ""
              }`}
              onClick={() => setSelected("되는시간")}
            >
              되는시간
            </div>
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${
                selected === "ToDo"
                  ? "text-scarlet border-b-2 border-scarlet"
                  : ""
              }`}
              onClick={() => setSelected("ToDo")}
            >
              ToDo
            </div>
            <div
              className={`justify-center max-md:px-5 cursor-pointer ${
                selected === "멤버"
                  ? "text-scarlet border-b-2 border-scarlet"
                  : ""
              }`}
              onClick={() => setSelected("멤버")}
            >
              멤버
            </div>
          </div>
        </div>
        {selected === "HOME" ? (
          <MoimHome notices={notices} />
        ) : selected === "되는시간" ? (
          <AvailableTime />
        ) : selected === "ToDo" ? (
          <ToDo />
        ) : selected === "멤버" ? (
          <Member />
        ) : null}
      </div>
      <Modal
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        setMessage={setMessage}
        message={message}
      />
    </MoimContainer>
  );
}

export default MoimJoinPage;
