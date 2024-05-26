import { useEffect, useState } from "react";
import { GET } from "../../utils/axios";
import { useParams } from "react-router";
import alarm from "../../assets/svg/Alarmclock_duotone-1.svg";
import user from "../../assets/svg/User_duotone.svg";
import ContentIndex from "./ContentIndex";
function MettingDetailPage() {
  const [meetingInfo, setMeetingInfo] = useState([]);
  const { meetingId } = useParams();

  const getMeetingInfo = async () => {
    try {
      const result = await GET(`api/meetings/detail/${meetingId}`);
      console.log(result);
      setMeetingInfo(result);
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    getMeetingInfo();
    console.log("h");
    console.log(meetingInfo.members);
  }, []);

  return (
    <div>
      <div className="grid gap-5">
        <div className="grid gap-2">
          <button className=" w-fit text-base font-light bg-scarlet text-white font-Roboto_Flex rounded-full px-3">
            참여중
          </button>
          <div className=" font-Pretendard_Black text-4xl">
            {meetingInfo.agenda}
          </div>
        </div>
        <hr />
        <div className="grid grid-4">
          <ContentIndex image={alarm} indexName={"미팅 시간정보"} />
          <div className=" font-Pretendard_Medium">
            {meetingInfo.startDateTime} ~ {meetingInfo.endDateTime}
          </div>
        </div>
        <div className="grid grid-4">
          <ContentIndex image={user} indexName={"참여 멤버"} />
          <div className="flex gap-3 font-Pretendard_Light">
            {meetingInfo.members &&
              meetingInfo.members.map((member, index) => {
                return (
                  <div key={index} className="flex flex-col justify-center">
                    <img
                      src={member.memberProfileImageUrl}
                      className=" w-11 h-11"
                    />
                    <div className=" text-xs">{member.username}</div>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MettingDetailPage;
