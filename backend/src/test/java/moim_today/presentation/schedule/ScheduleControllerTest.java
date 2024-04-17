package moim_today.presentation.schedule;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import moim_today.application.schedule.ScheduleService;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.fake_class.schedule.FakeScheduleService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.EVERY_TIME_ID;
import static moim_today.util.TestConstant.WRONG_EVERY_TIME_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScheduleControllerTest extends ControllerTest {

    private final ScheduleService scheduleService = new FakeScheduleService();

    @Override
    protected Object initController() {
        return new ScheduleController(scheduleService);
    }

    @DisplayName("요청 URL로 시간표 정보를 스케줄로 등록한다.")
    @Test
    void fetchTimeTable() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 04);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        TimeTableRequest timeTableRequest = new TimeTableRequest(
                        EVERY_TIME_ID.value(), startDate, endDate
                );

        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(timeTableRequest);

        mockMvc.perform(post("/api/schedules/timetable")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("에브리타임 URL 공유로 시간표 스케줄 등록",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("에브리타임 URL 공유")
                                .requestFields(
                                        fieldWithPath("everytimeId").type(STRING).description("에브리타임 id"),
                                        fieldWithPath("startDate").type(ARRAY).description("시작 기간"),
                                        fieldWithPath("endDate").type(ARRAY).description("종료 기간")
                                )
                                .build()
                        )));
    }

    @DisplayName("요청 URL로 시간표 정보를 스케줄로 등록한다.")
    @Test
    void fetchTimeTableFail() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 04);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        TimeTableRequest timeTableRequest = new TimeTableRequest(
                WRONG_EVERY_TIME_ID.value(), startDate, endDate
        );

        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(timeTableRequest);

        mockMvc.perform(post("/api/schedules/timetable")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("에브리타임 URL 공유로 시간표 스케줄 등록",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("에브리타임 URL 공유")
                                .requestFields(
                                        fieldWithPath("everytimeId").type(STRING).description("에브리타임 id"),
                                        fieldWithPath("startDate").type(ARRAY).description("시작 기간"),
                                        fieldWithPath("endDate").type(ARRAY).description("종료 기간")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }
}