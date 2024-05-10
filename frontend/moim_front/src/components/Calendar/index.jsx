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
let eventGuid = 0;

export default function Calendar({ selectedDate }) {
  const calendarRef = useRef(null); // 1. useRef를 사용하여 ref 생성
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Get current year
    const currentYear = new Date().getFullYear();

    // Fetch all events on component mount
    fetchAllEvents(currentYear).catch(console.error);

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (calendarRef.current && selectedDate) {
      calendarRef.current.getApi().gotoDate(selectedDate.toDate());
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
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  }

  // Function to map event data
  function mapEventData(event) {
    return {
      id: event.scheduleId,
      title: event.scheduleName,
      start: event.startDateTime,
      end: event.endDateTime,
      allDay: false, // Assuming all events fetched are not all-day events
      backgroundColor: event.colorHex,
    };
  }

  // Function to handle date selection
  function handleDateSelect(selectInfo) {
    let title = prompt("Please enter a new title for your event");
    let calendarApi = selectInfo.view.calendar;

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
      clickInfo.event.remove();
    }
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
