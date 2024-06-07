// React
import { useState, useEffect, useRef } from "react";

// API
import axios from "axios";

// UI
import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";

// Calendar
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";


const modalTheme = {
  root: {
    base: "fixed inset-x-0 top-0 z-50 h-screen overflow-y-auto overflow-x-hidden md:inset-0 md:h-full",
    show: {
      on: "flex bg-gray-900 bg-opacity-50 dark:bg-opacity-80",
      off: "hidden",
    },
    sizes: {
      sm: "max-w-sm",
      md: "max-w-md",
      lg: "max-w-lg",
      xl: "max-w-xl",
      "2xl": "max-w-2xl",
      "3xl": "max-w-3xl",
      "4xl": "max-w-4xl",
      "5xl": "max-w-5xl",
      "6xl": "max-w-6xl",
      "7xl": "max-w-7xl",
    },
    positions: {
      "top-left": "items-start justify-start",
      "top-center": "items-start justify-center",
      "top-right": "items-start justify-end",
      "center-left": "items-center justify-start",
      center: "items-center justify-center",
      "center-right": "items-center justify-end",
      "bottom-right": "items-end justify-end",
      "bottom-center": "items-end justify-center",
      "bottom-left": "items-end justify-start",
    },
  },
  content: {
    base: "relative h-full w-full p-4 md:h-auto",
    inner:
      "relative flex max-h-[90dvh] flex-col rounded-xl bg-white shadow dark:bg-gray-700",
  },
  body: {
    base: "flex-1 overflow-auto p-6",
    popup: "pt-0",
  },
  header: {
    base: "flex items-start justify-between rounded-t border-b mt-0.5 p-5 dark:border-gray-600",
    popup: "border-b-0 p-2",
    title:
      "pt-0.5 text-xl font-Pretendard_SemiBold text-gray-900 dark:text-white",
    close: {
      base: "ml-auto inline-flex items-center rounded-lg bg-transparent p-1.5 text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900 dark:hover:bg-gray-600 dark:hover:text-white",
      icon: "h-5 w-5",
    },
  },
  footer: {
    base: "flex items-center space-x-2 rounded-b border-gray-200 p-6 dark:border-gray-600",
    popup: "border-t",
  },
};

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
  handleDelete,
  memberId,
  isRefresh = false,
  setIsRefresh = null,
}) {
  const calendarRef = useRef(null);
  const [events, setEvents] = useState([]);
  const [calendarStart, setCalendarStart] = useState(new Date());
  const [loadedMonths, setLoadedMonths] = useState(new Set());
  const currentYear = new Date().getFullYear();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [selectedEventId, setSelectedEventId] = useState(null);

  const getMemberWeekly = async (memberId) => {
    //모임 내 멤버 별 가용시간
    try {
      let allEvents = [];

      // Parse calendar start date. expression is like "2024-06-01"
      var parseDate = calendarStart.toISOString().split("T")[0];

      const memberSchedule = await axios.get(
        `https://api.moim.today/api/schedules/weekly/available-time/members/${memberId}`,
        {
          params: {
            startDate: parseDate,
          },
        }
      );
      allEvents = [
        ...allEvents,
        ...memberSchedule.data.data.map((event) =>
          mapEventData(event, true, true)
        ),
      ];
      setEvents(allEvents);
    } catch (e) {
      console.log(e);
    }
  };

  const onDateChanged = (dateInfo) => {
    const date = new Date(dateInfo.startStr);
    setCalendarStart(date);
    fetchMonthEvents(date.getFullYear(), date.getMonth() + 1);
  };

  useEffect(() => {
    if (isPersonal) {
      fetchMonthEvents(currentYear, calendarStart.getMonth() + 1).catch(console.error);
    } else if (isMeeting) {
      memberId != null ? fetchAvailables() : null;
    } else if (isAvailable) {
      fetchAvailables();
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (calendarRef.current && selectedDate) {
      Promise.resolve().then(() => {
        calendarRef.current.getApi().gotoDate(selectedDate);
      });
    }
  }, [selectedDate]);


  useEffect(() => {
    if (setIsRefresh) {
      setIsRefresh(false);

      // Reset all events and fetch new events
      setEvents([]);
      setLoadedMonths(new Set());
      
      var year = calendarStart.getFullYear();

      // Get new events
      if (isMeeting) {
        memberId != null ? fetchAvailables() : null;
      } else {
        fetchMonthEvents(year, calendarStart.getMonth() + 1).catch(console.error);
      }

      if (isPersonal) {
        setIsRefresh(false);
        fetchMonthEvents(year, calendarStart.getMonth() + 1);
      }
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isRefresh]);

  useEffect(() => {
    if (memberId != null) getMemberWeekly(memberId);
    else {
      fetchAvailables();
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [memberId, calendarStart]);

  async function fetchMonthEvents(year, month) {
    try {
      if (loadedMonths.has(`${year}-${month}`)) return;
      const response = await axios.get(
        `https://api.moim.today/api/schedules/monthly?yearMonth=${year}-${month < 10 ? "0" + month : month}`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const newEvents = response.data.data.map((event) => mapEventData(event, false));
      setEvents((prevEvents) => [...prevEvents, ...newEvents]);
      setLoadedMonths((prev) => new Set(prev).add(`${year}-${month}`));
    } catch (error) {
      console.error("Error fetching events:", error);
    }
  }

  async function fetchAvailables() {
    //모임 가용시간
    try {
      let allEvents = [];
      const isPast = new Date(calendarStart) < new Date();
      const startDate = isPast
        ? new Date().toISOString().split("T")[0]
        : calendarStart instanceof Date
        ? calendarStart.toISOString().split("T")[0]
        : new Date(calendarStart).toISOString().split("T")[0];

      if (moimId == null) return;

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
        ...response.data.data.map((event) => mapEventData(event, true, true)),
      ];
      setEvents(allEvents);
    } catch (e) {
      console.log(e);
    }
  }

  // Function to map event data
  function mapEventData(event, backgroundEvent, calendar) {
    const formattedEvent = {
      id: calendar ? event.calendarId : event.scheduleId,
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
      console.log("handleDateSelected", selectInfo);
    }
    let calendarApi = selectInfo.view.calendar;
    if (isMeeting || isPersonal) {
      setShowModal(true);
    }

    setStartDateTime(selectInfo.startStr);
    setEndDateTime(selectInfo.endStr);

    calendarApi.unselect(); // clear date selection
  }

  function handleEventClick(info) {
    if (isPersonal) {
      setModalMessage("일정을 삭제하시겠습니까?");
      setSelectedEventId(info.event.id);
      setIsModalOpen(true);
    }
  }

  function handleEventChange(info) {
    console.log("Event changed:", info.event);
  }

  function handleEventRemove(info) {
    console.log("Event removed:", info.event);
  }

  async function deleteEvent() {
    try {
      await axios.delete(`https://api.moim.today/api/schedules`, {
        data: {
          scheduleId: selectedEventId,
        },
      });
      setIsModalOpen(false);
      
      // Refresh the calendar
      setIsRefresh(true);
    } catch (error) {
      console.error("Error deleting event:", error);
    }
  }

  return (
    <>
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
            eventClick={handleEventClick}
            eventContent={renderEventContent}
            eventChange={handleEventChange}
            eventRemove={handleEventRemove}
            datesSet={(dateInfo) => {
              onDateChanged(dateInfo);
            }}
          />
        </div>
      </div>
      <Modal
        show={isModalOpen}
        size="sm"
        onClose={() => setIsModalOpen(false)}
        theme={modalTheme}
        popup
      >
        <Modal.Header />
        <Modal.Body>
          <div className="text-center">
            <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
            <h3 className="mb-5 text-base font-Pretendard_Normal text-black">
              {modalMessage}
            </h3>
            <div className="flex justify-center gap-4">
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={() => {
                  deleteEvent();
                }}
              >
                네
              </button>
              <button
                className="py-3 px-5 w-fit text-base font-Pretendard_Normal text-white bg-scarlet rounded-[50px] hover:cursor-pointer"
                onClick={() => {
                  setIsModalOpen(false);
                }}
              >
                아니요
              </button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
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
