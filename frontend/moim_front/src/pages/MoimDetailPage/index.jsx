// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";

// API
import axios from "axios";

// Components
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";

// UI
import NewModal from "../../components/NewModal";
import MoimContainer from "../../components/PageContainer/MoimContainer";

function MoimDetailPage() {
  const { MoimId } = useParams();
  const [message, setMessage] = useState("");
  const [moimInfo, setMoimInfo] = useState([]);
  const [writerInfo, setWriterInfo] = useState([]);

  //modal
  const [isAlertOpen, setAlertOpen] = useState(false);

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
    <MoimContainer>
      <DetailedLeft
        userName={writerInfo.username}
        title={moimInfo.title}
        currentCount={moimInfo.currentCount}
        capacity={moimInfo.capacity}
        category={moimInfo.category}
        contents={moimInfo.contents}
        image={moimInfo.imageUrl}
        profileImg={writerInfo.memberProfileImageUrl}
        joined={false}
      />
      <div className="flex flex-col min-h-screen max-h-full w-full bg-white shadow-lg overflow-hidden rounded-t-3xl px-20 pt-8 pb-6 gap-8 max-sm:px-4">
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
    </MoimContainer>
  );
}

export default MoimDetailPage;
