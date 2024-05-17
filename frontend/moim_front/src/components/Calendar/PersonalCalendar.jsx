// React
import { useState, useEffect, useRef } from "react";

// Axios
import axios from "axios";

// Full Calendar
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

// Temporary event ID generator
// let eventGuid = 0;

export default function Calendar({
  selectedDate,
  isPersonal,
  isMeeting,
  moimId,
}) {
  const calendarRef = useRef(null); // 1. useRef를 사용하여 ref 생성
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Get current year
    const currentYear = new Date().getFullYear();

    // Fetch all events on component mount
    // fetchAllEvents(currentYear).catch(console.error);

    if (isPersonal) {
      fetchAllEvents(currentYear).catch(console.error);
    } else if (isMeeting) {
      //isMeeting이 true일 경우
      fetchAvailables();
    }
  }, []);

  useEffect(() => {
    if (calendarRef.current && selectedDate) {
      calendarRef.current.getApi().gotoDate(selectedDate);
    }
  }, [selectedDate]);

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
            // Add any other necessary headers or configurations
          }
        );
        allEvents = [...allEvents, ...response.data.data.map(mapEventData)];
      }
      setEvents(allEvents);
      console.log(events);
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  }

  async function fetchAvailables() {
    try {
      let allEvents = [];
      const response = await axios.get(
        `https://api.moim.today/api/schedules/weekly/available-time/moims/${moimId}`,
        {
          params: {
            startDate: "2024-05-14",
          },
        }
      );
      allEvents = [...allEvents, ...response.data.data.map(mapEventData)];
      setEvents(allEvents);
      console.log(events);
      console.log(response.data.data);
    } catch (e) {
      console.log(e);
    }
  }

  // Function to map event data
  function mapEventData(event) {
    return {
      id: event.calendarId,
      title: event.scheduleName || "",
      start: event.startDateTime.replace(" ", "T"),
      end: event.endDateTime.replace(" ", "T"),
      allDay: false, // Assuming all events fetched are not all-day events
      backgroundColor: event.colorHex,
    };
  }

  // Function to handle date selection
  function handleDateSelect(selectInfo) {
    let title = prompt(selectInfo);
    let calendarApi = selectInfo.view.calendar;
    console.log(selectInfo);

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

  // Function to handle event click
  function handleEventClick(clickInfo) {
    if (
      confirm(
        `Are you sure you want to delete the event '${clickInfo.event.title}'`
      )
    ) {
      // clickInfo.event.remove();
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

      await axios.post("https://api.moim.today/api/schedules", eventData, {
        headers: {
          "Content-Type": "application/json",
        },
      });

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

  // Function to create unique event ID
  function createEventId() {
    return String(eventGuid++);
  }

  return (
    <div className="demo-app">
      <div className="demo-app-main">
        <FullCalendar
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
          eventContent={renderEventContent} // custom render function
          eventClick={handleEventClick}
          // you can update a remote database when these fire:
          /*
        eventAdd={function(){}}
        eventChange={function(){}}
        eventRemove={function(){}}
        */
          eventAdd={handleEventAdd}
          eventChange={handleEventChange}
          eventRemove={handleEventRemove}
        />
      </div>
    </div>
  );
}

function renderEventContent(eventInfo) {
  return (
    <>
      <b>{eventInfo.timeText}</b>
      <i>{eventInfo.event.title}</i>
    </>
  );
}