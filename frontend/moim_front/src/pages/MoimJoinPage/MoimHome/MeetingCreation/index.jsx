import React, { useEffect, useState } from "react";
import Calendar from "../../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";
import CreationModal from "../../../../components/CreationModal";
import DropDown from "../../../../components/Dropdown/Simple";
import { POST } from "../../../../utils/axios";

function MeetingCreation({ moimId }) {
  const [showModal, setShowModal] = useState(false);
  const [startDateTime, setStartDateTime] = useState("");
  const [endDateTime, setEndDateTime] = useState("");
  const [agenda, setAgenda] = useState("");
  const [place, setPlace] = useState("");
  const [meetingCategory, setMeetingCategory] = useState("");
  const { MoimId } = useParams();

  const onSelect = (option) => {
    console.log(option);
    setMeetingCategory(option);
  };
  const createMeeting = async () => {
    const data = {
      moimId: moimId,
      agenda: agenda,
      startDateTime: startDateTime.replace("T", " ").split("+")[0],
      endDateTime: endDateTime.replace("T", " ").split("+")[0],
      place: place,
      meetingCategory: "REGULAR",
    };
    console.log("create Meeting");
    try {
      const response = await POST("api/meetings", data);
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    console.log(startDateTime);
    console.log(endDateTime);
    console.log(showModal);
  }, [showModal, startDateTime, endDateTime]);

  return (
    <div>
      <div className=" font-Pretendard_Black">가용시간</div>
      <Calendar
        isPersonal={false}
        isMeeting={true}
        moimId={MoimId}
        showModal={showModal}
        agenda={agenda}
        setAgenda={setAgenda}
        setShowModal={setShowModal}
        setStartDateTime={setStartDateTime}
        setEndDateTime={setEndDateTime}
      />
      <CreationModal
        showModal={showModal}
        setShowModal={setShowModal}
        noticeHandler={null}
        isMeeting={true}
        closeHandler={createMeeting}
        moimId={MoimId}
        agenda={agenda}
        setAgenda={setAgenda}
        setStartDateTime={setStartDateTime}
        startDateTime={startDateTime}
        setEndDateTime={setEndDateTime}
        endDateTime={endDateTime}
        place={place}
      >
        <div className="font-Pretendard_Black text-3xl pb-8">미팅 생성하기</div>
        <div className="flex flex-col gap-4">
          <div className="flex flex-col items-start justify-center">
            <div>
              <div className=" font-Pretendard_Black flex">미팅 카테고리</div>
              <DropDown options={["REGULAR"]} onSelect={onSelect} />
            </div>

            <div className="gap-4">
              <div className="flex font-Pretendard_Black ">미팅 의제</div>
              <input
                className="flex items-center justify-center focus:outline-none"
                placeholder={"미팅 의제를 입력해주세요"}
                onChange={(e) => {
                  setAgenda(e.target.value);
                }}
              />
            </div>
            <div>
              <div className=" font-Pretendard_Black flex">미팅 장소</div>
              <input
                className="flex items-stretch justify-center focus:outline-none"
                placeholder={"미팅 장소를 입력해주세요"}
                onChange={(e) => {
                  setPlace(e.target.value);
                }}
              />
            </div>
          </div>
        </div>
      </CreationModal>
    </div>
  );
}

export default MeetingCreation;
