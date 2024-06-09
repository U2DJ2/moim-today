import { useEffect, useState } from "react";
import { GET, POST } from "../../utils/axios";
import { useNavigate, useParams } from "react-router";
import alarm from "../../assets/svg/Alarmclock_duotone-1.svg";
import pin from "../../assets/svg/Pin_duotone.svg";
import user from "../../assets/svg/User_duotone.svg";
import content from "../../assets/svg/comment_duotone.svg";
import ContentIndex from "./ContentIndex";
import infoIcon from "../../assets/svg/Info_duotone_line.svg";
import CardBtn from "../MoimJoinPage/CardComponent/CardBtn";
import formatDate from "../../utils/formatDate";
import axios from "axios";
import NewModal from "../../components/NewModal";
import EditIcon from "../../assets/svg/Edit.svg";
import TrashIcon from "../../assets/svg/Trash_duotone.svg";
function MettingDetailPage() {
  const [meetingInfo, setMeetingInfo] = useState([]);
  const [comment, setComment] = useState("");
  const [commentList, setCommentList] = useState([]);
  const [commentNum, setCommentNum] = useState();
  const [attendance, setAttendance] = useState(false);
  const { meetingId } = useParams();
  const { MoimId } = useParams();

  //Modal
  const [alertMessage, setAlertMessage] = useState("");
  const [isAlertOpen, setAlertOpen] = useState(false);
  const [isEditOpen, setIsEditOpen] = useState(false);
  const [isDelete, setIsDelete] = useState(false);

  const [isHost, setIsHost] = useState(false);

  const [confirmType, setConfirmType] = useState("");

  const [editAgenda, setEditAgenda] = useState("");
  const [editPlace, setEditPlace] = useState("");

  const closeAlert = () => {
    setAlertOpen(false);
  };
  const deleteAction = () => {
    setAlertOpen(true);
    setAlertMessage("해당 미팅을 삭제하시겠습니까?");
    setIsDelete(true);
  };

  const confirmBtnContents = {
    attendanceBtn: {
      handler: closeAlert,
    },
    meetingDeleteBtn: {
      handler: deleteAction,
    },
  };

  const navigate = useNavigate();
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
      setComment("");
    } catch (e) {
      console.log(e);
    }
  };

  const acceptMeeting = async () => {
    try {
      const response = await POST(
        `api/members/meetings/${meetingId}/acceptance`
      );
      setAlertOpen(true);
      setConfirmType("attendance");
      setAlertMessage("미팅에 참석되어 스케줄에 추가됩니다.");
      checkIsMember();
      getMeetingInfo();
    } catch (e) {
      setAlertOpen(true);
      setAlertMessage(e.response.data.message);
    }
  };
  const cancelMeeting = async () => {
    try {
      const response = await POST(`api/members/meetings/${meetingId}/refusal`);
      setAlertOpen(true);
      setConfirmType("attendance");
      setAlertMessage("미팅에 참석을 취소했습니다.");
      checkIsMember();
      getMeetingInfo();
    } catch (e) {
      console.log(e);
    }
  };

  const deleteMeeting = async () => {
    try {
      const result = await axios.delete(
        `https://api.moim.today/api/meetings/${meetingId}`
      );
    } catch (e) {
      console.log(e);
    }
  };

  const onClickHandler = async () => {
    try {
      attendance ? await cancelMeeting() : await acceptMeeting();
    } catch (e) {
      console.log(e);
    }
  };
  const onClickDeleteHandler = async () => {
    try {
      await deleteMeeting();
      setAlertOpen(true);
      setConfirmType("meetingDelete");
      setAlertMessage("미팅을 삭제했습니다.");
    } catch (e) {
      console.log(e);
      g;
    }
  };

  const getHost = async () => {
    try {
      const result = await GET(`api/members/${MoimId}/hosts`);
      setIsHost(result.isHost);
    } catch (e) {
      console.log(e);
    }
  };

  const MeetingEditHandler = async () => {
    const body = {
      meetingId: meetingId,
      agenda: editAgenda,
      place: editPlace,
    };
    try {
      const result = await axios.patch(
        "https://api.moim.today/api/meetings",
        body
      );
      setIsEditOpen(false);
      getMeetingInfo();
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    getMeetingInfo();
    getComments();
    checkIsMember();
    getHost();
  }, []);

  const ContentSection = ({ image, indexName, children }) => (
    <div className="grid grid-4 gap-2 font-Pretendard_Light text-xl font-light">
      <ContentIndex image={image} indexName={indexName} />
      {children}
    </div>
  );
  return (
    <div>
      <NewModal
        show={isAlertOpen}
        size="sm"
        onClose={() => setAlertOpen(false)}
      >
        <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
          {alertMessage}
        </h3>
        <button
          className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
          onClick={() =>
            confirmType === "attendance" ? setAlertOpen(false) : navigate(-1)
          }
        >
          확인
        </button>
      </NewModal>
      <div className="grid gap-10">
        <div className="grid gap-2">
          <button
            className="w-fit text-sm font-light bg-scarlet text-white font-Pretendard_Light rounded-full px-2 py-1"
            onClick={onClickHandler}
          >
            {attendance ? "현재 참석중" : "현재 미참석"}
          </button>
          <div className="flex items-center">
            <img src={infoIcon} className=" w-4 h-4" />
            <div className=" font-Pretendard_Light text-slate-400 text-xs">
              참여 여부를 변경하고 싶으면, 위 아이콘을 눌러주세요.
            </div>
          </div>
          <div className="flex gap-4 items-end">
            <div className=" font-Pretendard_Black text-4xl">
              {meetingInfo.agenda}
            </div>
            {isHost ? (
              <div
                className="flex font-Pretendard_Light hover:cursor-pointer"
                onClick={() => console.log("clicked")}
              >
                <div className="flex items-center gap-4">
                  <div className="flex">
                    <img src={EditIcon} className="w-4 h-4 items-end " />
                    <p
                      className=" text-sm text-slate-500 hover:text-scarlet"
                      onClick={() => setIsEditOpen(true)}
                    >
                      수정하기
                    </p>
                  </div>

                  <div className="flex">
                    <img src={TrashIcon} className="w-4 h-4 items-end" />
                    <p
                      className="text-sm text-slate-500 hover:text-scarlet"
                      onClick={onClickDeleteHandler}
                    >
                      삭제하기
                    </p>
                  </div>
                </div>
              </div>
            ) : null}
          </div>
        </div>
        <hr />
        <ContentSection image={alarm} indexName="미팅 시간정보">
          {meetingInfo.startDateTime && formatDate(meetingInfo.startDateTime)} ~
          {meetingInfo.endDateTime && formatDate(meetingInfo.endDateTime)}
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
              <div key={index} className="flex gap-8">
                <div className="grid justify-items-center">
                  <img
                    src={comment.imageUrl}
                    className="rounded-full w-5 h-5"
                  />
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
      <NewModal
        show={isEditOpen}
        size="sm"
        onClose={() => setIsEditOpen(false)}
      >
        <div className="font-Pretendard_SemiBold text-2xl pb-10">
          미팅 수정하기
        </div>
        <div className="grid gap-8 justify-center">
          <div className="grid justify-center font-Pretendard_Light gap-4">
            <div className="text-left justify-center">
              <div className=" font-Pretendard_Medium">미팅 의제</div>
              <input
                placeholder="미팅 의제를 입력해주세요."
                className=" focus:outline-none"
                onChange={(e) => setEditAgenda(e.target.value)}
              />
            </div>
            <div className="text-left">
              <div className=" font-Pretendard_Medium">장소</div>
              <input
                placeholder="미팅 장소를 입력해주세요."
                className=" focus:outline-none"
                onChange={(e) => setEditPlace(e.target.value)}
              />
            </div>
          </div>
          <button
            className="py-3 px-5 pt-3 w-full text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
            onClick={() => MeetingEditHandler()}
          >
            완료
          </button>
        </div>
      </NewModal>
    </div>
  );
}

export default MettingDetailPage;
