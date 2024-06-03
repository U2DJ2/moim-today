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
  const [message, setMessage] = useState("");
  const [moimInfo, setMoimInfo] = useState([]);
  const [writerInfo, setWriterInfo] = useState([]);

  //modal
  const [isAlertOpen, setAlertOpen] = useState(false);
  const [isEditOpen, setIsEditOpen] = useState(false);

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
    </div>
  );
}

export default MoimDetailPage;
