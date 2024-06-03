import icon from "../../assets/svg/personIcon.svg";
import clock from "../../assets/svg/clockIcon.svg";

function DetailedRight({ moimInfo }) {
  return (
    <div className="flex flex-1 flex-col bg-white shadow-[0px_4px_12px_rgba(0,_0,_0,_0.06)] overflow-hidden rounded-3xl px-12 pt-6 gap-8 min-h-[600px] h-fit">
      <div>
        <div className="bg-black font-Pretendard_Light text-lg rounded-full px-5 text-white justify-center items-center text-center w-fit mb-3">
          {moimInfo.moimCategory}
        </div>
        <div className=" font-Pretendard_Black text-4xl text-[#3F3F3F] mb-2">
          {moimInfo.title}
        </div>
        <div className="flex gap-4">
          <div className="flex gap-1 font-Pretendard_SemiBold text-xl text-[#6F6F6F] hover:cursor-pointer">
            <img src={icon} />
            <div className="flex">
              <div>{moimInfo.currentCount}</div>
              <div>/</div>
              <div>{moimInfo.capacity}</div>
            </div>
          </div>
        </div>
      </div>
      <div className="flex font-Pretendard_Light text-[#3F3F3F] text-3xl">
        {moimInfo.contents}
      </div>
      <div>
        <div className="font-Pretendard_Light">모임 기간</div>
        <div className="font-Pretendard_Medium">
          {moimInfo.startDate}~{moimInfo.endDate}
        </div>
      </div>
    </div>
  );
}

export default DetailedRight;
