// React
import { useEffect, useState } from "react";
import { useParams } from "react-router";

// API
import axios from "axios";

// Components
import DetailedLeft from "../../components/DetailedLeft";
import DetailedRight from "../../components/DetailedRight";
import Modal from "../../components/Modal/ModalTest";


function MoimDetailPage() {
  const { MoimId } = useParams();
  const [isOpen, setIsOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [moimInfo, setMoimInfo] = useState([]);

  const modalHandler = () => {
    setIsOpen(!isOpen);
  };

  useEffect(() => {
    const fetchMoimDetail = async () => {
      try {
        const response = await axios.get(
          `https://api.moim.today/api/moims/detail/${MoimId}`
        );
        setMoimInfo(response.data);
      } catch (error) {
        setMessage(error.response.data.message);
      }
    };
    fetchMoimDetail();
  }, []);

  return (
    <div className="bg-gradient-to-b  justify-center from-white to-[#F6F8FE] h-screen w-full min-h-[800px] px-9">
      <div className=" flex w-full ">
        <div className="flex gap-9 pt-2 flex-1 overflow-auto">
          <DetailedLeft
            userName={"작성자"}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            joined={false}
            image={moimInfo.imageUrl}
            setMessage={setMessage}
            setIsOpen={setIsOpen}
          />
          <DetailedRight
            category={moimInfo.category}
            title={moimInfo.title}
            currentCount={moimInfo.currentCount}
            capacity={moimInfo.capacity}
            contents={moimInfo.contents}
            className={"pl-3"}
          />
        </div>
      </div>
      <Modal
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        closeModal={modalHandler}
        message={message}
      />
    </div>
  );
}

export default MoimDetailPage;
