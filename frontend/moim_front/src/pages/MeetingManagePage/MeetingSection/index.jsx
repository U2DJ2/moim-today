import axios from "axios";
import { useState, useEffect } from "react";
import NewModal from "../.././../components/NewModal";
import CardComponent from "../../MoimJoinPage/CardComponent";

function MeetingSection() {
  const [selected, setSelected] = useState("전체");
  const [isShow, setShow] = useState(false);
  const [alertMessage, setAlert] = useState("");
  const [meetingInfo, setMeetingInfo] = useState([]);
  const meetingStatus = {
    전체: "ALL",
    "지난 미팅": "PAST",
    "다가오는 미팅": "UPCOMING",
  };
  const getMeetings = async () => {
    try {
      const result = await axios.get("https://api.moim.today/api/meetings", {
        params: {
          meetingStatus: meetingStatus[selected],
        },
      });
      setMeetingInfo(result.data.data);
    } catch (e) {
      setShow(true);
      setAlert(e.response.data.message);
    }
  };

  useEffect(() => {
    getMeetings();
  }, [selected]);

  return (
    <section className="flex flex-col w-full gap-3 pt-12 pb-7 bg-slate-50 rounded-[64px_64px_0px_0px] max-md:px-5">
      <div className=" px-12 gap-3">
        <h1 className="text-6xl font-black pb-3 font-Pretendard_Black text-black max-md:max-w-full max-md:text-4xl">
          My Meetings
        </h1>
        <div className="flex items-center self-start font-Pretendard_Medium font-normal text-black max-md:px-5 max-md:max-w-full">
          <div className="flex gap-3">
            <div
              className={`justify-center px-9 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "전체" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("전체")}
            >
              전체
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "지난 미팅" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("지난 미팅")}
            >
              지난 미팅
            </div>
            <div
              className={`justify-center px-6 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                selected === "다가오는 미팅" ? "bg-black text-white" : ""
              }`}
              onClick={() => setSelected("다가오는 미팅")}
            >
              다가오는 미팅
            </div>
          </div>
          {meetingInfo.map((meeting, index) => {
            return (
              <CardComponent
                key={meeting.meetingId}
                meetingId={meetingInfo.meetingId}
                startDate={meetingInfo.startDate}
                title={meetingInfo.agenda}
              />
            );
          })}
        </div>
      </div>
      <NewModal show={isShow} size="sm" onClose={() => setShow(false)}>
        {alertMessage}
      </NewModal>
    </section>
  );
}

export default MeetingSection;
