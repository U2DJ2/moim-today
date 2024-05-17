import React, { useState } from "react";
import Calendar from "../../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";
import CreationModal from "../../../../components/CreationModal";

function MeetingCreation({ moimId }) {
  const [showModal, setShowModal] = useState(false);
  const { MoimId } = useParams();
  return (
    <div>
      <div className=" font-Pretendard_Black">가용시간</div>
      <Calendar
        isPersonal={false}
        isMeeting={true}
        moimId={MoimId}
        setShowModal={setShowModal}
      />
      <CreationModal
        showModal={showModal}
        setShowModal={setShowModal}
        noticeHandler={null}
      >
        <div className="font-Pretendard_Black">미팅 생성</div>
        <div className="flex flex-col">
          <div>미팅 의제</div>
          <input placeholder={"미팅 의제를 입력해주세요"} />
        </div>
      </CreationModal>
    </div>
  );
}

export default MeetingCreation;
