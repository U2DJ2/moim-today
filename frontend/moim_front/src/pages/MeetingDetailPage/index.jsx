import { useEffect, useState } from "react";
import { GET, POST } from "../../utils/axios";
import { useParams } from "react-router";
import alarm from "../../assets/svg/Alarmclock_duotone-1.svg";
import pin from "../../assets/svg/Pin_duotone.svg";
import user from "../../assets/svg/User_duotone.svg";
import content from "../../assets/svg/comment_duotone.svg";
import ContentIndex from "./ContentIndex";
import infoIcon from "../../assets/svg/Info_duotone_line.svg";
import CardBtn from "../MoimJoinPage/CardComponent/CardBtn";
import axios from "axios";
function MettingDetailPage() {
  const [meetingInfo, setMeetingInfo] = useState([]);
  const [comment, setComment] = useState("");
  const [commentList, setCommentList] = useState([]);
  const [commentNum, setCommentNum] = useState();
  const [attendance, setAttendance] = useState(false);
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

  const getComments = async () => {
    try {
      const result = await GET(`api/meeting-comments/${meetingId}`);
      setCommentNum(result.count);
      setCommentList(result.meetingCommentResponses);
    } catch (e) {
      console.log(e);
    }
  };

  const checkIsMember = async () => {
    try {
      const result = await axios.get(
        `https://api.moim.today/api/members/meetings/${meetingId}`
      );
      console.log(result.data);
      setAttendance(result.data.attendanceStatus);
    } catch (e) {
      console.log(e);
    }
  };
  const CommentClicker = async () => {
    const data = {
      meetingId: meetingId,
      contents: comment,
    };
    try {
      const result = await POST("api/meeting-comments", data);
      await getComments();
    } catch (e) {
      console.log(e);
    }
  };

  const acceptMeeting = async () => {
    try {
      const response = await POST(
        `api/members/meetings/${meetingId}/acceptance`
      );
      checkIsMember();
    } catch (e) {
      console.log(e);
    }
  };
  const cancelMeeting = async () => {
    try {
      const response = await POST(`api/members/meetings/${meetingId}/refusal`);
      checkIsMember();
    } catch (e) {
      console.log(e);
    }
  };

  const onClickHandler = async () => {
    console.log("first");
    try {
      attendance ? await cancelMeeting() : await acceptMeeting();
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getMeetingInfo();
    getComments();
    checkIsMember();
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
          <button
            className=" w-fit text-sm font-light bg-scarlet text-white font-Pretendard_Light rounded-full px-2 py-1"
            onClick={onClickHandler}
          >
            {attendance ? "참석중" : "미참석"}
          </button>
          <div className="flex items-center">
            <img src={infoIcon} className=" w-4 h-4" />
            <div className=" font-Pretendard_Light text-slate-400 text-xs">
              참여 여부를 변경하고 싶으면, 위 아이콘을 눌러주세요.
            </div>
          </div>

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
                      className=" w-11 h-11 rounded-full"
                    />
                    <div className=" text-xs">{member.username}</div>
                  </div>
                );
              })}
          </div>
        </div>
        <hr />
        <ContentIndex image={content} indexName={"댓글"} number={commentNum} />
        <div className="flex gap-8 ">
          <textarea
            className="w-1/2 flex items-center rounded-lg h-20 text-black text-sm border px-6 py-3 border-crimson font-Pretendard_Light placeholder:text-darkgray placeholder:justify-center focus:outline-none resize-none overflow-hidden"
            placeholder="댓글 작성하기"
            onChange={(e) => setComment(e.target.value)}
            value={comment}
          />
          <button
            className="text-white font-Pretendard_SemiBold bg-crimson rounded-lg w-24 h-12"
            onClick={CommentClicker}
          >
            REPLY
          </button>
        </div>
        {Array.isArray(commentList) && commentList.length != 0 ? (
          commentList.map((comment, index) => {
            return (
              <div key={index} className="flex gap-2">
                <div className="grid justify-items-center">
                  <img src={comment.imageUrl} className=" w-5 h-5" />
                  <p className=" font-Pretendard_Light text-slate-500 text-sm">
                    {comment.username}
                  </p>
                </div>

                <div className=" font-Pretendard_Medium">
                  {comment.contents}
                </div>
              </div>
            );
          })
        ) : (
          <p className=" font-Pretendard_Light text-slate-500">
            작성된 댓글이 없습니다.
          </p>
        )}
      </div>
    </div>
  );
}

export default MettingDetailPage;
