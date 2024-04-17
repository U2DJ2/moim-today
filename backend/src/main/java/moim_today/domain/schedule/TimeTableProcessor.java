package moim_today.domain.schedule;

import lombok.Builder;
import moim_today.dto.schedule.TimeTableRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record TimeTableProcessor(
        LocalDate startDate,
        LocalDate endDate,
        List<Schedule> schedules
) {

    public static TimeTableProcessor toDomain(final TimeTableRequest timeTableRequest) {
        return TimeTableProcessor.builder()
                .startDate(timeTableRequest.startDate())
                .endDate(timeTableRequest.endDate())
                .schedules(new ArrayList<>())
                .build();
    }

    public void processTimeTable(final NodeList subjects) {
        for (int i = 0; i < subjects.getLength(); i++) {
            processNode(subjects.item(i));
        }
    }

    private void processNode(final Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            processElement(element);
        }
    }

    private void processElement(final Element element) {
        String name = element.getElementsByTagName("name")
                .item(0).getAttributes().getNamedItem("value")
                .getNodeValue();

        NodeList times = element.getElementsByTagName("data");
        for (int j = 0; j < times.getLength(); j++) {
            processTimeElement(name, (Element) times.item(j));
        }
    }

    private void processTimeElement(final String name, final Element timeElement) {
        int day = Integer.parseInt(timeElement.getAttribute("day"));
        DayOfWeek dayOfWeek = DayOfWeek.of((day % 7) + 1);
        LocalTime startTime = LocalTime.ofSecondOfDay(Long.parseLong(timeElement.getAttribute("starttime")) * 300);
        LocalTime endTime = LocalTime.ofSecondOfDay(Long.parseLong(timeElement.getAttribute("endtime")) * 300);
        addSchedule(name, dayOfWeek, startTime, endTime);
    }

    private void addSchedule(final String name, final DayOfWeek dayOfWeek,
                             final LocalTime startTime, final LocalTime endTime) {
        LocalDate nextDate = adjustStartDate(startDate, dayOfWeek);

        while (!nextDate.isAfter(endDate)) {
            LocalDateTime startDateTime = LocalDateTime.of(nextDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(nextDate, endTime);
            schedules.add(new Schedule(1, 0, name, startDateTime, endDateTime));
            nextDate = nextDate.plusWeeks(1);
        }
    }

    private LocalDate adjustStartDate(final LocalDate startDate, final DayOfWeek startDayOfWeek) {
        int daysToAdd = (startDayOfWeek.getValue() - startDate.getDayOfWeek().getValue() + 7) % 7;
        return startDate.plusDays(daysToAdd);
    }
}
