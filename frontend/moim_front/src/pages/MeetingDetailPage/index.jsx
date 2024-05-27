import { useEffect, useState } from "react";
import { GET } from "../../utils/axios";
import { useParams } from "react-router";
import alarm from "../../assets/svg/Alarmclock_duotone-1.svg";
import pin from "../../assets/svg/Pin_duotone.svg";
import user from "../../assets/svg/User_duotone.svg";
import content from "../../assets/svg/comment_duotone.svg";
import ContentIndex from "./ContentIndex";
import CardBtn from "../MoimJoinPage/CardComponent/CardBtn";
function MettingDetailPage() {
  const [meetingInfo, setMeetingInfo] = useState([]);
  const [comment, setComment] = useState("");
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

  const ContentSection = ({ image, indexName, children }) => (
    <div className="grid grid-4 gap-2 font-Pretendard_Light text-2xl font-light">
      <ContentIndex image={image} indexName={indexName} />
      {children}
    </div>
  );
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
        <ContentSection image={alarm} indexName="미팅 시간정보">
          {meetingInfo.startDateTime} ~ {meetingInfo.endDateTime}
        </ContentSection>
        <ContentSection image={pin} indexName={"장소"}>
          {meetingInfo.place}
        </ContentSection>
        <div className="grid grid-4 gap-2">
          <ContentIndex image={user} indexName={"참여 멤버"} />
          <div className="flex gap-3 font-Pretendard_Light">
            {meetingInfo.members &&
              meetingInfo.members.map((member, index) => {
                return (
                  <div key={index} className="flex flex-col items-center gap-4">
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
        <hr />
        <ContentIndex image={content} indexName={"댓글"} />
        <div className="border">
          <textarea
            className="w-1/2 flex items-center rounded-lg h-20 text-black text-sm placeholder:text-darkgray placeholder:justify-center focus:outline-none resize-none overflow-hidden"
            placeholder="댓글 작성하기"
            onChange={(e) => setComment(e.target.value)}
            value={comment}
          />
        </div>
      </div>
    </div>
  );
}

export default MettingDetailPage;
