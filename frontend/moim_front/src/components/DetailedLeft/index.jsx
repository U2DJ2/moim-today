// React
import { useState } from "react";
import { useNavigate, useParams } from "react-router";

// API
import axios from "axios";
import NewModal from "../NewModal";

// Images
import icon from "../../assets/svg/personIcon.svg";

function DetailedLeft({
  userName,
  title,
  currentCount,
  capacity,
  joined,
  image,
  profileImg,
}) {
  let { MoimId } = useParams();
  console.log(MoimId);

  const navigate = useNavigate();
  const [isAlertModalOpen, setAlertModalOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [status, setStatus] = useState(false);

  const body = {
    moimId: MoimId,
  };

  const onClickHandler = () => {
    axios
      .post("https://api.moim.today/api/moims/members", body)
      .then(() => {
        setStatus(true);
        setAlertModalOpen(true);
      })
      .catch((error) => {
        // setIsOpen(true);
        setStatus(false);
        setMessage(error.response.data.message);
        setAlertModalOpen(true);
      });
  };

  return (
    <>
      <NewModal
        show={isAlertModalOpen}
        size="sm"
        onClose={() => setAlertModalOpen(false)}
      >
        <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
          {message ? message : "모임에 가입되셨습니다!"}
        </h3>
        <div className="flex justify-center gap-4">
          <button
            className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
            onClick={() => {
              status ? navigate(`/join/${MoimId}`) : setAlertModalOpen(false);
            }}
          >
            확인
          </button>
        </div>
      </NewModal>

      <div className="flex flex-col gap-4 items-center h-full w-96 max-md:w-full">
        <img className="rounded-t-2xl" src={image} />
        <div className="flex flex-col gap-2 items-center">
          <img className="w-10 h-10 rounded-full" src={profileImg} />
          <div className="font-Pretendard_Normal ">{userName}</div>
        </div>

        <div className="text-center font-Pretendard_Black text-2xl text-[#3F3F3F]">
          {title}
        </div>
        <div className="flex gap-1 font-Pretendard_SemiBold text-sm text-[#6F6F6F] hover:cursor-pointer hover:text-scarlet">
          <img src={icon} />
          <div className="flex">
            <div>{currentCount}</div>
            <div>/</div>
            <div>{capacity}</div>
          </div>
        </div>

        <button
          className={`${
            joined ? "hidden" : "flex"
          } w-52 justify-center px-14 py-4 text-xl text-center text-white bg-black whitespace-nowrap rounded-[50px] font-Pretendard_Normal hover:cursor-pointer hover:bg-scarlet`}
          onClick={onClickHandler}
        >
          🏳️ 참여하기
        </button>
      </div>
    </>
  );
}

export default DetailedLeft;
