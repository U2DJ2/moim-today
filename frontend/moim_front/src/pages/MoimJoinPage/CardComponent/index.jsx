import axios from "axios";

function CardComponent({
  month,
  day,
  dday,
  dayOfWeek,
  title,
  btn,
  clickHandler,
  isMeeting,
  meetingId,
}) {
  const onClickHandler = async () => {
    try {
      const response = await axios.post(
        `https://api.moim.today/api/members/meetings/${meetingId}/acceptance`
      );
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };
  return (
    <div
      className="w-auto p-8 grid font-Pretendard_SemiBold text-xl bg-white shadow-[0px_1px_16px_rgba(0,_0,_0,_0.08)] rounded-3xl hover:cursor-pointer"
      onClick={clickHandler}
    >
      <div className="grid grid-flow-row gap-4">
        {isMeeting ? null : (
          <div className=" text-base font-Pretendard_Light">
            {month}월 {day}일 ({dayOfWeek})
          </div>
        )}
        <div className="ml-1">
          <div className="grid grid-rows-2 w-fit">
            <div className=" font-Pretendard_Medium font-bold text-2xl">
              {title}
            </div>
            {isMeeting ? (
              <div className="text-scarlet text-base">D-{dday}</div>
            ) : null}
          </div>
        </div>
        <div className="pt-2"></div>
        {btn && (
          <button
            className="text-white bg-scarlet p-1.5 rounded-full font-Pretendard_SemiBold hover:bg-slate-400"
            onClick={onClickHandler}
          >
            참석하기
          </button>
        )}
      </div>
    </div>
  );
}

export default CardComponent;
