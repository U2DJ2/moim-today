package moim_today.implement.schedule.schedule;

import moim_today.domain.schedule.TimeTableParser;
import moim_today.domain.schedule.TimeTableProcessor;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.annotation.Implement;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.NodeList;


import static moim_today.global.constant.EveryTimeConstant.*;
import static moim_today.global.constant.SymbolConstant.*;


@Implement
public class ScheduleFetcher {

    private final RestTemplate restTemplate;

    public ScheduleFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchTimetable(final String everytimeUrl) {
        String url = REQUEST_TIME_TABLE_URL.value();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HTTP_HEADER_ORIGIN.value(), EVERYTIME_ORIGIN.value());
        headers.set(HTTP_HEADER_REFERER.value(), EVERYTIME_REFERER.value());
        headers.set(HTTP_HEADER_USER_AGENT.value(), EVERYTIME_USER_AGENT.value());

        int atIndex = everytimeUrl.indexOf(AT.value());
        String everytimeId = everytimeUrl.substring(atIndex + 1);

        String postData = EVERYTIME_ID.value() + everytimeId + EVERYTIME_FRIEND_INFO.value();
        HttpEntity<String> request = new HttpEntity<>(postData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }

    public TimeTableProcessor processTimetable(final String timeTableXML,
                                               final TimeTableRequest timeTableRequest,
                                               final int colorCount) {

        TimeTableParser timeTableParser = TimeTableParser.toDomain(timeTableXML);
        NodeList subjects = timeTableParser.getSubjects();

        TimeTableProcessor timeTableProcessor = TimeTableProcessor.toDomain(timeTableRequest, colorCount);
        timeTableProcessor.processTimeTable(subjects);

        return timeTableProcessor;
    }
}
