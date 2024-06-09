import axios from "axios";
import { useMemo, useState } from "react";
import CardBtn from "./CardBtn";

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
  joinAvailability = null,
  initialAttendance,
  startDate,
  attendance,
  moimTitle = null,
}) {
  // const [attendance, setAttendance] = useState(initialAttendance);
  const meetingCancel = async () => {
    try {
      const response = await axios.post(
        `https://api.moim.today/api/members/meetings/${meetingId}/refusal`
      );
      return response;
    } catch (e) {
      console.log(e);
    }
  };
  const meetingJoin = async () => {
    try {
      const response = await axios.post(
        `https://api.moim.today/api/members/meetings/${meetingId}/acceptance`
      );
      return response;
    } catch (e) {
      console.log(e);
    }
  };
  const onClickHandler = async (attendance) => {
    if (attendance) {
      try {
        const result = await meetingCancel();
        setAttendance(false);
        console.log(result);
      } catch (e) {
        console.log(e);
      }
    } else {
      try {
        const result = await meetingJoin();
        console.log(result);
        setAttendance(true);
      } catch (e) {
        console.log(e);
      }
    }
  };
  const dateConverter = useMemo(() => {
    const modifiedDday = dday * -1;
    return dday === 0
      ? "D-DAY"
      : modifiedDday > 0
      ? `D+${modifiedDday}`
      : `D${modifiedDday}`;
  }, [dday]);

  return (
    <div
      className="w-auto h-fit p-8 grid font-Pretendard_SemiBold text-xl bg-white shadow-[0px_1px_16px_rgba(0,_0,_0,_0.08)] rounded-3xl hover:cursor-pointer max-2xl:p-4 max-2xl:text-sm"
      onClick={clickHandler}
    >
      <div className="grid grid-flow-row gap-2">
        {isMeeting ? null : (
          <div className=" text-base font-Pretendard_Light">
            {month}월 {day}일 ({dayOfWeek})
          </div>
        )}
        <div className="ml-1">
          <div className="grid grid-rows-2 w-fit">
            <div className="flex items-center gap-4">
              <div>{startDate}</div>
              {isMeeting ? (
                <div className="text-xl text-scarlet ">{dateConverter}</div>
              ) : null}
            </div>
            <div className="flex flex-col gap-1">
              {moimTitle ? (
                <div className="flex items-center font-Pretendard_Light text-xs w-fit text-slate-500">
                  {moimTitle}
                </div>
              ) : null}
              <div className=" font-Pretendard_Medium font-bold text-2xl">
                {title}
              </div>
            </div>
            {joinAvailability === true ? (
              <div className="font-Pretendard_Light text-slate-400 text-xs">
                해당 시간에 미팅을 참석할 수 있습니다.
              </div>
            ) : (
              <div className="font-Pretendard_Light text-scarlet text-xs">
                해당 시간에 이미 일정이 존재합니다.
              </div>
            )}
          </div>
        </div>

        {btn && (
          <CardBtn
            name={attendance ? "취소하기" : "참여하기"}
            clickHandler={() => onClickHandler(attendance)}
          />
        )}
      </div>
    </div>
  );
}

export default CardComponent;
