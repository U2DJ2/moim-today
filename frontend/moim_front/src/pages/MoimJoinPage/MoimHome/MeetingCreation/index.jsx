import React from "react";
import Calendar from "../../../../components/Calendar/PersonalCalendar";
import { useParams } from "react-router";

function MeetingCreation({ moimId }) {
  const { MoimId } = useParams();
  return (
    <div>
      <Calendar isPersonal={false} isMeeting={true} moimId={MoimId} />
    </div>
  );
}

export default MeetingCreation;
