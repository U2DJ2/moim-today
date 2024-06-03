// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";

// API
import axios from "axios";

// Components
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";

// UI

import { HiOutlineExclamationCircle } from "react-icons/hi";
import NewModal from "../../components/NewModal";

function MoimDetailPage() {
  const { MoimId } = useParams();
  const [isAlertOpen, setAlertOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [moimInfo, setMoimInfo] = useState([]);
  const [writerInfo, setWriterInfo] = useState([]);

  //modal
  const [showModal, setShowMoal] = useState(false);
  const fetchWriter = async () => {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/members/host-profile/${MoimId}`
      );
      setWriterInfo(response.data);
    } catch (e) {
      console.log(e);
    }
  };

  const fetchMoimDetail = async () => {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/moims/detail/${MoimId}`
      );
      console.log(response.data);
      setMoimInfo(response.data);
    } catch (error) {
      setMessage(error.response.data.message);
    }
  };

  useEffect(() => {
    fetchWriter();
    fetchMoimDetail();
  }, []);

  return (
    <div className="bg-gradient-to-b justify-center from-white to-[#F6F8FE] pl-10">
      <div className="flex gap-8 pt-2 flex-1 overflow-auto">
        <DetailedLeft
          userName={writerInfo.username}
          title={moimInfo.title}
          currentCount={moimInfo.currentCount}
          capacity={moimInfo.capacity}
          joined={false}
          image={moimInfo.imageUrl}
          setMessage={setMessage}
          message={message}
          setIsOpen={setAlertOpen}
        />
        <DetailedRight moimInfo={moimInfo} className={"pl-3"} />
      </div>
      <NewModal
        show={isAlertOpen}
        size="sm"
        onClose={() => setAlertOpen(false)}
      >
        <div className="text-center">
          <h3 className="mb-5 text-base font-Pretendard_Normal text-center text-black">
            {message}
          </h3>
          <div className="flex justify-center gap-4">
            <button
              className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
              onClick={() => {
                setAlertOpen(false);
              }}
            >
              확인
            </button>
          </div>
        </div>
      </NewModal>
      <CreationModal
        showModal={showModal}
        setShowModal={setShowModal}
        noticeHandler={null}
        isMeeting={true}
        moimId={MoimId}
        agenda={agenda}
        setAgenda={setAgenda}
        setStartDateTime={setStartDateTime}
        startDateTime={startDateTime}
        setEndDateTime={setEndDateTime}
        endDateTime={endDateTime}
        meetingCategory={meetingCategory}
        place={place}
        setOpen={setOpen}
        isRefresh={isRefresh}
        setIsRefresh={setIsRefresh}
      >
        <div className="font-Pretendard_Black text-3xl pb-8">미팅 수정하기</div>
        <div className="flex flex-col gap-4">
          <div className="flex flex-col items-start justify-center mx-auto gap-4">
            <div className="grid gap-1">
              <div className=" font-Pretendard_Black flex">미팅 카테고리</div>
              <DropDown
                options={["정기미팅", "단기미팅"]}
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
    </div>
  );
}

export default MoimDetailPage;
