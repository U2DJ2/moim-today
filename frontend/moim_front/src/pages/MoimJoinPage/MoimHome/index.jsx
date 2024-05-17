import React, { useEffect, useState } from "react";
import CardComponent from "../CardComponent";
import SimpleDrop from "../../../components/Dropdown/Simple";
import { useNavigate, useParams } from "react-router";
import { GET, POST } from "../../../utils/axios";
import CreationModal from "../../../components/CreationModal";
import { SettingsOutlined } from "@mui/icons-material";
function MoimHome({
  notices,
  meetings,
  meetingOption,
  setMeetingOption,
  isHost,
  moimId,
}) {
  const [showModal, setShowModal] = useState(false);
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");

  const [noticeTitle, setNoticeTitle] = useState("");
  const [noticeContent, setNoticeContent] = useState("");
  const [noticeDate, setNoticeDate] = useState("");

  const navigate = useNavigate();

  let noticeId = 1,
    meetingId = 1;
  const onSelect = (option) => {
    console.log(option);
    setMeetingOption(option);
  };
  const moreNoticeHandler = () => {
    navigate(`notice/${noticeId}`);
  };
  const cardClickHandler = () => {
    navigate(`meeting/${meetingId}`);
  };

  const makeNoticeHandler = () => {
    setShowModal(!showModal);
    console.log(showModal);
  };
  const makeMeetingHandler = () => {
    navigate(`/meeting/${moimId}`);
  };
  const noticeHandler = () => {
    const data = {
      moimId: moimId,
      title: title,
      contents: contents,
    };
    const postNotice = async () => {
      try {
        const response = await POST("api/moims/notices", data);
        console.log(response.data);
      } catch (e) {
        console.log(e);
      }
    };
    postNotice();
  };

  return (
    <div className="flex flex-col gap-24">
      <div className="grid gap-4">
        <div className="flex gap-4 text-center">
          <div className="text-4xl font-Pretendard_Black">공지사항</div>
          <div
            className="flex items-center font-Pretendard_Light hover:text-scarlet hover:cursor-pointer"
            onClick={moreNoticeHandler}
          >
            더보기
          </div>
        </div>

        <div className="flex gap-12">
          {isHost ? (
            <button
              onClick={makeNoticeHandler}
              className="hover:cursor-pointer hover:text-scarlet"
            >
              공지사항 생성하기
            </button>
          ) : null}
          {notices.length !== 0 ? (
            notices.map((notice, index) => (
              <CardComponent
                key={index}
                date={notice.createdAt}
                title={notice.title}
                btn={false}
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
          <div className="text-4xl font-Pretendard_Black pb-4">
            미팅 확인하기
          </div>
          <SimpleDrop
            options={["다가오는 미팅", "지나간 미팅"]}
            onSelect={onSelect}
          />
        </div>
        {isHost ? (
          <button
            onClick={makeMeetingHandler}
            className="hover:cursor-pointer hover:text-scarlet"
          >
            미팅 생성하기
          </button>
        ) : null}

        {meetings.length != 0 ? (
          <CardComponent
            date={"4월 15일 (월)"}
            dday={"D-1"}
            title={"{Gathering_Title}"}
            btn={true}
            clickHandler={cardClickHandler}
          />
        ) : (
          <div className="font-Pretendard_Light flex">
            생성된 미팅이 없습니다.
          </div>
        )}
      </div>
      {showModal && (
        <CreationModal
          showModal={showModal}
          setShowModal={setShowModal}
          closeHandler={noticeHandler}
        >
          <div className="flex flex-col">
            <div className="">
              <div>제목</div>
              <input
                placeholder="제목을 입력해주세요"
                onChange={(e) => {
                  setTitle(e.target.value);
                }}
              />
            </div>
            <div>
              <div>내용</div>
              <input
                placeholder="내용을 입력해주세요"
                onChange={(e) => {
                  setContents(e.target.value);
                }}
              />
            </div>
          </div>
        </CreationModal>
      )}
    </div>
  );
}

export default MoimHome;
