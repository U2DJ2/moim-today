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
  initialAttendance,
  startDate,
}) {
  const [attendance, setAttendance] = useState(initialAttendance);
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
            <div className="flex items-center gap-4">
              <div>{startDate}</div>
              {isMeeting ? (
                <div className="text-xl text-scarlet ">{dateConverter}</div>
              ) : null}
            </div>

            <div className=" font-Pretendard_Medium font-bold text-2xl">
              {title}
            </div>
          </div>
        </div>
        <div className="pt-2"></div>

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
