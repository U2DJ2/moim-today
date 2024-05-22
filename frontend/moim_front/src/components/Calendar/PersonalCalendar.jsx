// React
import { useState, useEffect, useRef } from "react";

// Axios
import axios from "axios";

// Full Calendar
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

export default function Calendar({
  selectedDate,
  isPersonal,
  isMeeting,
  isAvailable,
  moimId,
  title,
  showModal,
  setShowModal,
  endDateTime,
  setEndDateTime,
  startDateTime,
  setStartDateTime,
  setScheduleTitle,
  scheduleTitle,
  memberId,
}) {
  const calendarRef = useRef(null); // 1. useRef를 사용하여 ref 생성
  const today = new Date().toISOString().split("T")[0];
  const [events, setEvents] = useState([]);
  const [calendarStart, setCalendarStart] = useState(new Date());

  useEffect(() => {
    const currentYear = new Date().getFullYear();

    if (isPersonal) {
      fetchAllEvents(currentYear).catch(console.error);
    } else if (isMeeting) {
      fetchAvailables();
      fetchMeetings();
    } else if (isAvailable) {
      fetchAvailables();
    }
  }, []);

  useEffect(() => {
    fetchAvailables();
  }, [calendarStart]);

  useEffect(() => {
    if (calendarRef.current && selectedDate) {
      Promise.resolve().then(() => {
        calendarRef.current.getApi().gotoDate(selectedDate);
      });
    }
  }, [selectedDate]);

  useEffect(() => {
    const calendarApi = calendarRef.current.getApi();
    calendarApi.refetchEvents(); // 이벤트 데이터를 다시 가져옴
  }, [memberId]); // memberId가 변경될 때마다 실행

  // useEffect(() => {
  //   const GetMemberWeekly = async (memberId) => {
  //     try {
  //       let allEvents = [];
  //       const memberSchedule = await axios.get(
  //         `https://api.moim.today/api/schedules/weekly/available-time/members/${memberId}`,
  //         {
  //           params: {
  //             startDate: selectedDate.split("T")[0],
  //           },
  //         }
  //       );
  //       console.log(memberSchedule);
  //       allEvents = [
  //         ...allEvents,
  //         ...memberSchedule.data.data.map((event) => mapEventData(event, true)),
  //       ];
  //       return setEvents(allEvents);
  //     } catch (e) {
  //       console.log(e);
  //     }
  //   };
  //   console.log(memberId);
  //   GetMemberWeekly(memberId);
  // }, [memberId, selectedDate]);

  async function fetchAllEvents(year) {
    try {
      let allEvents = [];
      for (let month = 1; month <= 8; month++) {
        const response = await axios.get(
          `https://api.moim.today/api/schedules/monthly?yearMonth=${year}-${
            month < 10 ? "0" + month : month
          }`,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        allEvents = [
          ...allEvents,
          ...response.data.data.map((event) => mapEventData(event, false)),
        ];
      }
      setEvents(allEvents);
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  }

  async function fetchMeetings() {
    try {
      const response = await axios.get(
        `https://api.moim.today/api/meetings/${moimId}`,
        {
          params: {
            meetingStatus: "ALL",
          },
        }
      );
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  }

  async function fetchAvailables() {
    try {
      let allEvents = [];
      const isPast = new Date(calendarStart) < new Date();
      const startDate = isPast
        ? new Date().toISOString().split("T")[0]
        : calendarStart instanceof Date
        ? calendarStart.toISOString().split("T")[0]
        : new Date(calendarStart).toISOString().split("T")[0];
      const response = await axios.get(
        `https://api.moim.today/api/schedules/weekly/available-time/moims/${moimId}`,
        {
          params: {
            startDate: startDate,
          },
        }
      );
      allEvents = [
        ...allEvents,
        ...response.data.data.map((event) => mapEventData(event, true)),
      ];
      setEvents(allEvents);
    } catch (e) {
      console.log(e);
    }
  }

  // Function to map event data
  function mapEventData(event, backgroundEvent) {
    const formattedEvent = {
      id: event.scheduleId || event.calendarId,
      title: event.scheduleName || "",
      start: event.startDateTime.replace(" ", "T"),
      end: event.endDateTime.replace(" ", "T"),
      allDay: false, // Assuming all events fetched are not all-day events
      backgroundColor: event.colorHex,
    };
    if (backgroundEvent) {
      formattedEvent.display = "background";
    }
    return formattedEvent;
  }

  function handleDateSelect(selectInfo) {
    if (isAvailable) {
      console.log(selectInfo);
    }
    let calendarApi = selectInfo.view.calendar;
    if (isMeeting) {
      setShowModal(true);
    }

    setStartDateTime(selectInfo.startStr);
    setEndDateTime(selectInfo.endStr);

    calendarApi.unselect(); // clear date selection

    if (title) {
      calendarApi.addEvent({
        id: createEventId(),
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        allDay: selectInfo.allDay,
      });
    }
  }

  // Function to handle event add
  async function handleEventAdd(info) {
    try {
      const eventData = {
        scheduleName: info.event.title,
        startDateTime: info.event.start,
        endDateTime: info.event.end,
      };

      const result = await axios.post(
        "https://api.moim.today/api/schedules",
        eventData
      );

      console.log(
        "Event added and sent to the backend:",
        info.event.start,
        info.event.end
      );
    } catch (error) {
      console.error("Error adding event:", error);
    }
  }

  // Function to handle event change
  function handleEventChange(info) {
    console.log("Event changed:", info.event);
  }

  // Function to handle event remove
  function handleEventRemove(info) {
    console.log("Event removed:", info.event);
  }

  function personalSubmit() {
    calendarRef.current.getApi().unselect();
  }
  const handleDateSet = (dateInfo) => {
    fetchAvailables();
  };
  const onChangeDate = (dateInfo) => {
    console.log(dateInfo.startStr.split("T")[0]);
    setCalendarStart(dateInfo.startStr.split("T")[0]);
  };
  return (
    <div className="demo-app">
      <div className="demo-app-main">
        <FullCalendar
          key={memberId}
          ref={calendarRef} // 2. FullCalendar 컴포넌트에 ref 추가
          plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
          headerToolbar={{
            left: "prev,next today",
            center: "title",
            right: "",
            // right: "dayGridMonth,timeGridWeek",
          }}
          initialView="timeGridWeek"
          editable={true}
          selectable={true}
          selectMirror={true}
          dayMaxEvents={true}
          events={events}
          select={handleDateSelect}
          eventContent={renderEventContent}
          eventAdd={handleEventAdd}
          eventChange={handleEventChange}
          eventRemove={handleEventRemove}
          datesSet={(dateInfo) => {
            onChangeDate(dateInfo);
          }}
        />
      </div>
    </div>
  );
}

function renderEventContent(eventInfo) {
  return (
    <div className="flex flex-col">
      <b className="text-white font-Pretendard_SemiBold">
        {eventInfo.timeText}
      </b>
      <i className="text-white font-Pretendard_Normal">
        {eventInfo.event.title}
      </i>
    </div>
  );
}
