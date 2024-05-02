package moim_today.domain.schedule;

import lombok.Builder;
import moim_today.domain.schedule.enums.ColorHex;
import moim_today.dto.schedule.TimeTableRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static moim_today.global.constant.EveryTimeConstant.*;
import static moim_today.global.constant.NumberConstant.*;
import static moim_today.global.constant.TimeConstant.*;

@Builder
public record TimeTableProcessor(
        LocalDate startDate,
        LocalDate endDate,
        List<Schedule> schedules,
        Map<String, Integer> colorCount
) {

    public static TimeTableProcessor toDomain(final TimeTableRequest timeTableRequest) {
        return TimeTableProcessor.builder()
                .startDate(timeTableRequest.startDate())
                .endDate(timeTableRequest.endDate())
                .schedules(new ArrayList<>())
                .colorCount(new HashMap<>())
                .build();
    }

    public int getColorCountSize() {
        return colorCount.size();
    }

    public void processTimeTable(final NodeList subjects) {
        int subjectsLength = subjects.getLength();
        for (int i = 0; i < subjectsLength; i++) {
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
        String name = element.getElementsByTagName(EVERYTIME_NAME.value())
                .item(EVERYTIME_ITEM_START_INDEX.value())
                .getAttributes().getNamedItem(EVERYTIME_VALUE.value())
                .getNodeValue();

        NodeList times = element.getElementsByTagName(EVERYTIME_DATA.value());
        int length = times.getLength();

        IntStream.range(EVERYTIME_NODE_START_INDEX.value(), length)
                .forEach(
                        j -> processTimeElement(name, (Element) times.item(j))
                );
    }

    private void processTimeElement(final String name, final Element timeElement) {
        int day = Integer.parseInt(timeElement.getAttribute(EVERYTIME_DAY.value()));
        DayOfWeek dayOfWeek = DayOfWeek.of((day % DAYS_PER_WEEK.time()) + WEEK_START_POINT.time());
        LocalTime startTime = LocalTime.ofSecondOfDay(
                Long.parseLong(timeElement.getAttribute(EVERYTIME_START_TIME.value())) * FIVE_MINUTES_IN_SECONDS.time()
        );

        LocalTime endTime = LocalTime.ofSecondOfDay(
                Long.parseLong(timeElement.getAttribute(EVERYTIME_END_TIME.value())) * FIVE_MINUTES_IN_SECONDS.time()
        );
        addSchedule(name, dayOfWeek, startTime, endTime);
    }

    private void addSchedule(final String name, final DayOfWeek dayOfWeek,
                             final LocalTime startTime, final LocalTime endTime) {
        LocalDate nextDate = adjustStartDate(startDate, dayOfWeek);

        while (!nextDate.isAfter(endDate)) {
            LocalDateTime startDateTime = LocalDateTime.of(nextDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(nextDate, endTime);

            colorCount.putIfAbsent(name, colorCount.size());
            int count = colorCount.get(name);
            ColorHex hexByCount = ColorHex.getHexByCount(count);
            String colorHex = hexByCount.value();

            Schedule schedule = Schedule.toDomain(name, dayOfWeek, colorHex, startDateTime, endDateTime);
            schedules.add(schedule);
            nextDate = nextDate.plusWeeks(ONE_WEEK.time());
        }
    }

    private LocalDate adjustStartDate(final LocalDate startDate, final DayOfWeek startDayOfWeek) {
        int daysToAdd = (startDayOfWeek.getValue() - startDate.getDayOfWeek().getValue() + DAYS_PER_WEEK.time())
                % DAYS_PER_WEEK.time();
        return startDate.plusDays(daysToAdd);
    }
}
