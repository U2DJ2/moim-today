import React from "react";
import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";
import { useParams } from "react-router";
import axios from "axios";

function DetailedLeft({
  userName,
  title,
  currentCount,
  capacity,
  joined,
  image,
  setMessage,
  setIsOpen,
}) {
  let { MoimId } = useParams();
  console.log(MoimId);

  const body = {
    moimId: MoimId,
  };
  const onClickHandler = () => {
    axios
      .post("https://api.moim.today/api/moims/members", body)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        setIsOpen(true);
        setMessage(error.response.data.message);
      });
  };
  return (
    <div className="flex flex-col basis-1/5 gap-4 items-center h-full min-h-screen md:basis-1/6">
      <img className=" w-72 h-60 rounded-t-2xl" src={image} />
      <div className=" font-Pretendard_Normal ">{userName}</div>
      <div className="text-center font-Pretendard_Black text-3xl text-[#3F3F3F]">
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
      <div className="flex font-Pretendard_SemiBold text-sm text-[#6F6F6F] hover:cursor-pointer hover:text-scarlet">
        <img src={clock} />
        <span className="ml-2">가용시간 보기</span>
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
  );
}

export default DetailedLeft;
