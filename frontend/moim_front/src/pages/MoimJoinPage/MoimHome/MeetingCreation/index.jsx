import React, { useEffect, useState } from "react";
import Calendar from "../../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";
import CreationModal from "../../../../components/CreationModal";
import DropDown from "../../../../components/Dropdown/Simple";
import { POST } from "../../../../utils/axios";

// Material UI
import MuiAlert from "@mui/material/Alert";
import { Slide } from "@mui/material";

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

function MeetingCreation() {
  const [showModal, setShowModal] = useState(false);
  const [startDateTime, setStartDateTime] = useState("");
  const [endDateTime, setEndDateTime] = useState("");
  const [agenda, setAgenda] = useState("");
  const [place, setPlace] = useState("");
  const [meetingCategory, setMeetingCategory] = useState("REGULAR");
  const { MoimId } = useParams();

  const [open, setOpen] = useState(false);

  const onSelect = (option) => {
    console.log(option);
    option === "정기모임"
      ? setMeetingCategory("REGULAR")
      : setMeetingCategory("SINGLE");
  };

  const createMeeting = async () => {
    const data = {
      moimId: moimId,
      agenda: agenda,
      startDateTime: startDateTime.replace("T", " ").split("+")[0],
      endDateTime: endDateTime.replace("T", " ").split("+")[0],
      place: place,
      meetingCategory: meetingCategory,
    };
    console.log("create Meeting");
    try {
      const response = await POST("api/meetings", data);
      console.log(response);
      setOpen(true);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="flex flex-col gap-3 px-10">
      <div className="font-Pretendard_Black text-4xl">가용시간</div>
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
        setOpen={setOpen}
      >
        <div className="font-Pretendard_Black text-3xl pb-8">미팅 생성하기</div>
        <div className="flex flex-col gap-4">
          <div className="flex flex-col items-start justify-center mx-auto gap-4">
            <div className="grid gap-1">
              <div className=" font-Pretendard_Black flex">미팅 카테고리</div>
              <DropDown
                options={["정기모임", "단기모임"]}
                onSelect={onSelect}
              />
            </div>

            <div className="grid gap-1">
              <div className="flex font-Pretendard_Black ">미팅 의제</div>
              <input
                className="flex items-center justify-center focus:outline-none"
                placeholder={"미팅 의제를 입력해주세요"}
                onChange={(e) => {
                  setAgenda(e.target.value);
                }}
              />
            </div>
            <div className="grid grid-1">
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
      <Slide direction="up" in={open} mountOnEnter unmountOnExit>
        <div style={{ position: "fixed", bottom: 20, right: 20, zIndex: 50 }}>
          <Alert severity="success" onClose={() => setOpen(false)}>
            미팅 생성이 완료됐습니다
          </Alert>
        </div>
      </Slide>
    </div>
  );
}

export default MeetingCreation;
