import axios from "axios";
import { useState, useEffect } from "react";
import NewModal from "../.././../components/NewModal";
import CardComponent from "../../MoimJoinPage/CardComponent";
import { useNavigate } from "react-router";

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
  const navigate = useNavigate();
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
  const meetingCardHandler = (meetingId, moimId) => {
    navigate(`/meeting/${moimId}/${meetingId}`);
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
        <div className="flex flex-col items-start self-start font-Pretendard_Medium font-normal text-black gap-6 max-md:px-5 max-md:max-w-full">
          <div className="flex gap-3">
            {Object.keys(meetingStatus).map((status, index) => (
              <div
                key={index}
                className={`justify-center px-9 py-3 rounded-[64px] max-md:px-5 cursor-pointer ${
                  selected === status ? "bg-black text-white" : ""
                }`}
                onClick={() => setSelected(status)}
              >
                {status}
              </div>
            ))}
          </div>
          <div className="grid sm:grid-cols-1 md:grid-row-2 lg:grid-cols-4 gap-4 ">
            {meetingInfo.length != 0 ? (
              meetingInfo.map((meeting) => {
                return (
                  <CardComponent
                    key={meeting.meetingId}
                    date={meeting.date}
                    isMeeting={true}
                    btn={false}
                    moimTitle={meeting.moimTitle}
                    meetingId={meeting.meetingId}
                    dday={meeting.dDay}
                    startDate={meeting.startDate}
                    title={meeting.agenda}
                    clickHandler={() =>
                      meetingCardHandler(meeting.meetingId, meeting.moimId)
                    }
                  />
                );
              })
            ) : (
              <div>미팅이 없습니다.</div>
            )}
          </div>
        </div>
      </div>
      <NewModal show={isShow} size="sm" onClose={() => setShow(false)}>
        {alertMessage}
      </NewModal>
    </section>
  );
}

export default MeetingSection;
