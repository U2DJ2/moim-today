import React from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import Calendar from "react-awesome-calendar";

function MonthlyTodo() {
  return (
    <div>
      <Calendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        events={[
          // 이벤트 데이터를 여기에 추가하세요
          // 예: { title: "이벤트 제목", date: "2024-05-01" }
          {
            id: 1,
            color: "#fd3153",
            from: "2024-05-12T18:00:00+00:00",
            to: "2024-05-12T19:00:00+00:00",
            title: "This is an event",
          },
        ]}
      />{" "}
    </div>
  );
}

export default MonthlyTodo;
