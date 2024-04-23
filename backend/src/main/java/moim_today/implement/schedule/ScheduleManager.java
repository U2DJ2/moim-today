package moim_today.implement.schedule;

import moim_today.domain.schedule.Schedule;
import moim_today.domain.schedule.TimeTableParser;
import moim_today.domain.schedule.TimeTableProcessor;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.dto.schedule.TimeTableSchedulingTask;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.NodeList;

import java.util.List;

import static moim_today.global.constant.EveryTimeConstant.*;
import static moim_today.global.constant.NumberConstant.TIME_TABLE_SCHEDULING_COUNT;
import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_TABLE_SCHEDULING_ERROR;

@Implement
public class ScheduleManager {

    private final RestTemplate restTemplate;
    private final ScheduleAppender scheduleAppender;
    private final SchedulingTaskQueue schedulingTaskQueue;

    public ScheduleManager(final RestTemplate restTemplate, final ScheduleAppender scheduleAppender,
                           final SchedulingTaskQueue schedulingTaskQueue) {
        this.restTemplate = restTemplate;
        this.scheduleAppender = scheduleAppender;
        this.schedulingTaskQueue = schedulingTaskQueue;
    }

    public String fetchTimetable(final String everytimeId) {
        String url = REQUEST_TIME_TABLE_URL.value();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HTTP_HEADER_ORIGIN.value(), EVERYTIME_ORIGIN.value());
        headers.set(HTTP_HEADER_REFERER.value(), EVERYTIME_REFERER.value());
        headers.set(HTTP_HEADER_USER_AGENT.value(), EVERYTIME_USER_AGENT.value());

        String postData = EVERYTIME_ID.value() + everytimeId + EVERYTIME_FRIEND_INFO.value();
        HttpEntity<String> request = new HttpEntity<>(postData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }

    public List<Schedule> processTimetable(final String timeTableXML,
                                           final TimeTableRequest timeTableRequest) {
        TimeTableParser timeTableParser = TimeTableParser.toDomain(timeTableXML);
        NodeList subjects = timeTableParser.getSubjects();

        TimeTableProcessor timeTableProcessor = TimeTableProcessor.toDomain(timeTableRequest);
        timeTableProcessor.processTimeTable(subjects);

        return timeTableProcessor.schedules();
    }

    // 0.1초에 한번씩 실행
    @Scheduled(fixedRate = 100)
    public void processSchedulingTasks() {
        for (int i = 0; i < TIME_TABLE_SCHEDULING_COUNT.value() && !schedulingTaskQueue.isEmpty(); i++) {
            TimeTableSchedulingTask timeTableSchedulingTask = schedulingTaskQueue.peek();
            boolean success = false;

            try {
                scheduleAppender.batchUpdateSchedules(timeTableSchedulingTask);
                success = true;
            } catch (DataAccessException e) {
                throw new InternalServerException(TIME_TABLE_SCHEDULING_ERROR.value());

            } finally {
                if (success) {
                    schedulingTaskQueue.poll();
                }
            }
        }
    }
}
