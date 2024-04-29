package moim_today.presentation.schedule;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.schedule.ScheduleService;
import moim_today.dto.schedule.ScheduleCreateRequest;
import moim_today.dto.schedule.ScheduleDeleteRequest;
import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.fake_class.schedule.FakeScheduleService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.EVERY_TIME_ID;
import static moim_today.util.TestConstant.WRONG_EVERY_TIME_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScheduleControllerTest extends ControllerTest {

    private final ScheduleService scheduleService = new FakeScheduleService();

    @Override
    protected Object initController() {
        return new ScheduleController(scheduleService);
    }

    @DisplayName("캘린더에 나타낼 한 달 스케줄을 조회한다.")
    @Test
    void findAllByMonthly() throws Exception {
        mockMvc.perform(get("/api/schedules-monthly")
                        .param("yearMonth", "2024-03")
                )
                .andExpect(status().isOk())
                .andDo(document("캘린더에 나타낼 한 달 스케줄 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("스케줄 조회")
                                .queryParameters(
                                        parameterWithName("yearMonth").description("연도 - 월 정보, ex) 2024-03")
                                )
                                .responseFields(
                                        fieldWithPath("data[0].scheduleId").type(NUMBER).description("스케줄 id"),
                                        fieldWithPath("data[0].meetingId").type(NUMBER).description("미팅 id"),
                                        fieldWithPath("data[0].scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("data[0].dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("data[0].startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("data[0].endDateTime").type(STRING).description("종료 시간")
                                )
                                .build()
                        )));
    }

    @DisplayName("요청 URL로 시간표 정보를 스케줄로 등록한다.")
    @Test
    void fetchTimeTable() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 04);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        TimeTableRequest timeTableRequest = new TimeTableRequest(
                        EVERY_TIME_ID.value(), startDate, endDate
                );

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
                                        fieldWithPath("startDate").type(STRING).description("시작 기간"),
                                        fieldWithPath("endDate").type(STRING).description("종료 기간")
                                )
                                .build()
                        )));
    }

    @DisplayName("요청 URL이 올바르지 않으면 예외가 발생한다.")
    @Test
    void fetchTimeTableFail() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 04);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        TimeTableRequest timeTableRequest = new TimeTableRequest(
                WRONG_EVERY_TIME_ID.value(), startDate, endDate
        );

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
                                        fieldWithPath("startDate").type(STRING).description("시작 기간"),
                                        fieldWithPath("endDate").type(STRING).description("종료 기간")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("개인 일정을 생성한다.")
    @Test
    void createSchedule() throws Exception {
        ScheduleCreateRequest scheduleCreateRequest = ScheduleCreateRequest.builder()
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        String json = objectMapper.writeValueAsString(scheduleCreateRequest);

        mockMvc.perform(post("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("개인 일정 등록 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 등록")
                                .requestFields(
                                        fieldWithPath("scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("종료 시간")
                                )
                                .build()
                        )));
    }

    @DisplayName("이미 존재하는 시간대에 개인 일정을 등록하면 예외가 발생한다.")
    @Test
    void createScheduleFail() throws Exception {
        ScheduleCreateRequest scheduleCreateRequest = ScheduleCreateRequest.builder()
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        String json = objectMapper.writeValueAsString(scheduleCreateRequest);

        mockMvc.perform(post("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("개인 일정 등록 실패 - 이미 존재하는 시간대의 스케줄",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 등록")
                                .requestFields(
                                        fieldWithPath("scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("종료 시간")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("개인 일정을 수정한다.")
    @Test
    void updateSchedule() throws Exception {
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(1L)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        String json = objectMapper.writeValueAsString(scheduleUpdateRequest);

        mockMvc.perform(patch("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("개인 일정 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 수정")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id"),
                                        fieldWithPath("scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("종료 시간")
                                )
                                .build()
                        )));
    }

    @DisplayName("변경하려는 시간에 다른 일정이 있으면 수정에 실패한다")
    @Test
    void updateScheduleBadRequest() throws Exception {
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(1L)
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        String json = objectMapper.writeValueAsString(scheduleUpdateRequest);

        mockMvc.perform(patch("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("개인 일정 수정 실패 - 해당 시간에 다른 일정이 있음",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 수정")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id"),
                                        fieldWithPath("scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("종료 시간")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("회원의 스케줄이 아니면 수정할 수 없다.")
    @Test
    void updateScheduleForbidden() throws Exception {
        ScheduleUpdateRequest scheduleUpdateRequest = ScheduleUpdateRequest.builder()
                .scheduleId(Long.parseLong(FORBIDDEN_SCHEDULE_ID.value()))
                .scheduleName(SCHEDULE_NAME.value())
                .dayOfWeek(DayOfWeek.MONDAY)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        String json = objectMapper.writeValueAsString(scheduleUpdateRequest);

        mockMvc.perform(patch("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isForbidden())
                .andDo(document("개인 일정 수정 실패 - 회원의 스케줄이 아님",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 수정")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id"),
                                        fieldWithPath("scheduleName").type(STRING).description("스케줄명"),
                                        fieldWithPath("dayOfWeek").type(STRING).description("요일"),
                                        fieldWithPath("startDateTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("종료 시간")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("개인 일정을 삭제한다.")
    @Test
    void deleteSchedule() throws Exception {
        ScheduleDeleteRequest scheduleDeleteRequest = new ScheduleDeleteRequest(1L);

        String json = objectMapper.writeValueAsString(scheduleDeleteRequest);

        mockMvc.perform(delete("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("개인 일정 삭제 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 삭제")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id")
                                )
                                .build()
                        )));
    }

    @DisplayName("회원의 스케줄이 아니면 삭제에 실패한다.")
    @Test
    void deleteScheduleForbidden() throws Exception {
        ScheduleDeleteRequest scheduleDeleteRequest = new ScheduleDeleteRequest(Long.parseLong(FORBIDDEN_SCHEDULE_ID.value()));

        String json = objectMapper.writeValueAsString(scheduleDeleteRequest);

        mockMvc.perform(delete("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isForbidden())
                .andDo(document("개인 일정 삭제 실패 - 회원의 스케줄이 아님",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 삭제")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("스케줄 정보를 찾을 수 없으면 삭제에 실패한다.")
    @Test
    void deleteScheduleNotFound() throws Exception {
        ScheduleDeleteRequest scheduleDeleteRequest = new ScheduleDeleteRequest(Long.parseLong(NOTFOUND_SCHEDULE_ID.value()));

        String json = objectMapper.writeValueAsString(scheduleDeleteRequest);

        mockMvc.perform(delete("/api/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("개인 일정 삭제 실패 - 일정을 찾을 수 없음",
                        resource(ResourceSnippetParameters.builder()
                                .tag("스케줄")
                                .summary("개인 일정 삭제")
                                .requestFields(
                                        fieldWithPath("scheduleId").type(NUMBER).description("스케줄 id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }
}