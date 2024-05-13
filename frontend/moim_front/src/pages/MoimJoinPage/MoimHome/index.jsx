import React from "react";
import CardComponent from "../CardComponent";
import SimpleDrop from "../../../components/Dropdown/Simple";
import { useNavigate, useParams } from "react-router";
function MoimHome() {
  const navigate = useNavigate();
  let noticeId = 1,
    meetingId = 1;
  const onSelect = (option) => {
    console.log(option);
  };
  const noticeHandler = () => {
    navigate(`notice/${noticeId}`);
  };
  const cardClickHandler = () => {
    navigate(`meeting/${meetingId}`);
  };
  return (
    <div className="flex flex-col gap-24">
      <div className="grid gap-4">
        <div className="flex gap-4 text-center">
          <div className="text-4xl font-Pretendard_Black">공지사항</div>
          <div
            className="flex items-center font-Pretendard_Light hover:text-scarlet hover:cursor-pointer"
            onClick={noticeHandler}
          >
            더보기
          </div>
        </div>

        <div className="flex gap-12">
          <CardComponent
            date={"4월 15일 (월)"}
            title={"{Announce_Title}"}
            btn={false}
          />
          <CardComponent
            date={"4월 15일 (월)"}
            title={"{Announce_Title}"}
            btn={false}
          />
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

        <CardComponent
          date={"4월 15일 (월)"}
          dday={"D-1"}
          title={"{Gathering_Title}"}
          btn={true}
          clickHandler={cardClickHandler}
        />
      </div>
    </div>
  );
}

export default MoimHome;
