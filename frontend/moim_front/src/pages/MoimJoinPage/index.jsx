// React
import { useState, useEffect } from "react";
import { useParams } from "react-router";

// Pages
import ToDo from "./ToDo";
import Member from "./Member";
import MoimHome from "./MoimHome";
import AvailableTime from "./AvailableTime";

// API
import { checkWriter } from "../../api/users";
import NewModal from "../../components/NewModal";

function MoimJoinPage() {
  const [isAlertModalOpen, setAlertModalOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [selected, setSelected] = useState("홈");
  const [isHost, setIsHost] = useState(false);

  const { MoimId } = useParams();

  const selectionKey = {
    홈: <MoimHome isHost={isHost} moimId={MoimId} />,
    "되는 시간": <AvailableTime moimId={MoimId} />,
    "할 일": <ToDo />,
    멤버: <Member isHost={isHost} MoimId={MoimId} />,
  };

  const getHost = async () => {
    try {
      const result = await checkWriter(MoimId);
      setIsHost(result.data.isHost);
    } catch (e) {
      setMessage(e.response.data.message);
    }
  };

  useEffect(() => {
    getHost();
  }, []);

  return (
    <>
      <div className="flex justify-center items-center w-fit self-start font-Pretendard_Black font-normal text-black max-md:px-5 max-md:w-full max-md:justify-between">
        <div className="flex gap-16 font-bold text-4xl  max max-lg:text-2xl max-lg:gap-10 max-md:text-xl max-md:gap-8 max-md:justify-between max-md:w-full">
          {Object.keys(selectionKey).map((key, index) => (
            <div
              key={index}
              className={`justify-center w-fit cursor-pointer ${
                selected === key
                  ? "text-scarlet border-b-4 pb-2 border-scarlet"
                  : ""
              }`}
              onClick={() => setSelected(key)}
            >
              {key}
            </div>
          ))}
        </div>
      </div>
      {selectionKey[selected]}

      <NewModal
        show={isAlertModalOpen}
        size="sm"
        onClose={() => setAlertModalOpen(false)}
      >
        <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
          {message}
        </h3>
        <div className="flex justify-center gap-4">
          <button
            className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
            onClick={() => {
              setAlertModalOpen(false);
            }}
          >
            확인
          </button>
        </div>
      </NewModal>
    </>
  );
}

export default MoimJoinPage;
