import React, { useEffect, useState } from "react";
import CardComponent from "../CardComponent";
import SimpleDrop from "../../../components/Dropdown/Simple";
import { useNavigate, useParams } from "react-router";
import { GET, POST } from "../../../utils/axios";
import CreationModal from "../../../components/CreationModal";
import { IndeterminateCheckBox, SettingsOutlined } from "@mui/icons-material";
function MoimHome({
  notices,
  meetings,
  meetingOption,
  setMeetingOption,
  isHost,
  moimId,
}) {
  const [showModal, setShowModal] = useState(false);

  const [noticeTitle, setNoticeTitle] = useState("");
  const [noticeContent, setNoticeContent] = useState("");
  const [noticeDate, setNoticeDate] = useState("");
  const [open, setOpen] = useState(false);

  const navigate = useNavigate();

  let noticeId = 1,
    meetingId = 1;
  const onSelect = (option) => {
    console.log(option);
    if (option === "다가오는 미팅") {
      setMeetingOption("UPCOMING");
    } else if (option === "지나간 미팅") {
      setMeetingOption("PAST");
    } else {
      setMeetingOption("ALL");
    }
  };

  const cardClickHandler = () => {
    navigate(`notice/${noticeId}`);
  };

  const makeNoticeHandler = () => {
    setShowModal(!showModal);
    console.log(showModal);
  };
  const makeMeetingHandler = () => {
    navigate(`/meeting/${moimId}`);
  };
  const postNotice = async () => {
    try {
      const data = {
        moimId: moimId,
        title: noticeTitle,
        contents: noticeContent,
      };
      const response = await POST("api/moims/notices", data);

      console.log(response.data);
    } catch (e) {
      console.log(e);
    }
  };
  const noticeHandler = () => {
    console.log("first");

    postNotice();
    // postNotice();
  };

  return (
    <div className="flex flex-col gap-24">
      <div className="grid gap-4">
        <div className="flex gap-4 text-center items-center">
          <div className="text-4xl font-Pretendard_Black ">공지사항</div>
          {isHost ? (
            <button
              onClick={makeNoticeHandler}
              className="hover:cursor-pointer bg-black text-white rounded-full h-fit px-4 py-1 font-Pretendard_Light hover:bg-scarlet"
            >
              공지사항 생성하기
            </button>
          ) : null}
        </div>

        <div className="grid grid-cols-3 gap-4">
          {notices.length !== 0 ? (
            notices.map((notice, index) => (
              <CardComponent
                key={index}
                month={notice.month}
                day={notice.day}
                dayOfWeek={notice.dayOfWeek}
                title={notice.title}
                btn={false}
                isMeeting={false}
                clickHandler={cardClickHandler}
              />
            ))
          ) : (
            <div className="font-Pretendard_Light flex">
              공지사항이 없습니다.
            </div>
          )}
        </div>
      </div>
      <div>
        <div className="pb-8">
          <div className="flex items-center gap-4 pb-4">
            <div className="text-4xl font-Pretendard_Black font-semibold">
              미팅 확인하기
            </div>
            {isHost ? (
              <button
                onClick={makeMeetingHandler}
                className="hover:cursor-pointer bg-black text-white rounded-full h-fit px-4 py-1 font-Pretendard_Light hover:bg-scarlet"
              >
                미팅 생성하기
              </button>
            ) : null}
          </div>

          <SimpleDrop
            options={["다가오는 미팅", "지나간 미팅"]}
            onSelect={onSelect}
          />
        </div>

        <div className="grid grid-cols-card gap-4">
          {meetings.length != 0 ? (
            meetings.map((meeting, index) => (
              <CardComponent
                key={index}
                date={meeting.date}
                startDate={meeting.startDate}
                dday={meeting.dDay}
                title={meeting.agenda}
                attendance={meeting.attendance}
                btn={false}
                isMeeting={true}
                meetingId={meeting.meetingId}
              />
            ))
          ) : (
            <div className="font-Pretendard_Light flex">
              생성된 미팅이 없습니다.
            </div>
          )}
        </div>
      </div>
      {showModal && (
        <CreationModal
          isMeeting={false}
          showModal={showModal}
          setShowModal={setShowModal}
          closeHandler={noticeHandler}
        >
          <div className="font-Roboto_Bold text-3xl pb-8">공지사항</div>
          <div className="flex flex-col gap-4">
            <div className="flex flex-col items-start justify-center mx-auto gap-4">
              <div className="gird gap-3">
                <div className=" font-Pretendard_Black text-xl text-start">
                  제목
                </div>
                <input
                  className=" font-Pretendard_Light border p-4 focus:outline-none"
                  placeholder="제목을 입력해주세요"
                  onChange={(e) => {
                    setNoticeTitle(e.target.value);
                  }}
                />
              </div>

              <div className="grid gap-1">
                <div className="font-Pretendard_Black text-xl text-start">
                  내용
                </div>
                <input
                  className="font-Pretendard_Light border p-4 focus:outline-none"
                  placeholder="내용을 입력해주세요"
                  onChange={(e) => {
                    setNoticeContent(e.target.value);
                  }}
                />
              </div>
            </div>
          </div>
        </CreationModal>
      )}
    </div>
  );
}

export default MoimHome;
