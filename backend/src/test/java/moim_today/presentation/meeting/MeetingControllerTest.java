package moim_today.presentation.meeting;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.meeting.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.domain.meeting.enums.MeetingStatus;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.fake_class.meeting.FakeMeetingService;
import moim_today.presentation.meeting.meeting.MeetingController;
import moim_today.util.ControllerTest;
import moim_today.util.EnumDocsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.domain.meeting.enums.MeetingStatus.*;
import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.MEETING_PLACE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeetingControllerTest extends ControllerTest {

    private final MeetingService meetingService = new FakeMeetingService();

    @Override
    protected Object initController() {
        return new MeetingController(meetingService);
    }

    @DisplayName("단일 미팅을 생성한다.")
    @Test
    void createSingleMeeting() throws Exception {
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(1)
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.SINGLE)
                .build();

        String json = objectMapper.writeValueAsString(meetingCreateRequest);

        mockMvc.perform(post("/api/meetings")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("단일 미팅 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 생성")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("startDateTime").type(STRING).description("미팅 시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("미팅 종료 시간"),
                                        fieldWithPath("place").type(STRING).description("미팅 장소"),
                                        fieldWithPath("meetingCategory").type(STRING).description("미팅 카테고리")
                                )
                                .build()
                        )));
    }

    @DisplayName("정기 미팅을 생성한다.")
    @Test
    void createRegularMeeting() throws Exception {
        MeetingCreateRequest meetingCreateRequest = MeetingCreateRequest.builder()
                .moimId(1)
                .agenda(MEETING_AGENDA.value())
                .startDateTime(LocalDateTime.of(2024, 3, 4, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 3, 4, 12, 0, 0))
                .place(MEETING_PLACE.value())
                .meetingCategory(MeetingCategory.REGULAR)
                .build();

        String json = objectMapper.writeValueAsString(meetingCreateRequest);

        mockMvc.perform(post("/api/meetings")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("정기 미팅 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 생성")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("startDateTime").type(STRING).description("미팅 시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("미팅 종료 시간"),
                                        fieldWithPath("place").type(STRING).description("미팅 장소"),
                                        fieldWithPath("meetingCategory").type(STRING).description("미팅 카테고리")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임내 다가오는 미팅 정보 조회한다.")
    @Test
    void findAllUpcomingByMoimId() throws Exception {
        mockMvc.perform(get("/api/meetings/{moimId}", 1L)
                        .param("meetingStatus", String.valueOf(UPCOMING))
                )
                .andExpect(status().isOk())
                .andDo(document("모임내 다가오는 미팅 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 목록 조회")
                                .responseFields(
                                        fieldWithPath("data[0].meetingId").type(NUMBER).description("미팅 id"),
                                        fieldWithPath("data[0].agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("data[0].startDate").type(STRING).description("미팅 시작 날짜"),
                                        fieldWithPath("data[0].dayOfWeek").type(VARIES).description(
                                                String.format("미팅 요일 - %s", EnumDocsUtils.getEnumNames(DayOfWeek.class))
                                        ),
                                        fieldWithPath("data[0].dDay").type(NUMBER).description("D-Day")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임내 이전 미팅 정보를 조회한다.")
    @Test
    void findAllPastByMoimId() throws Exception {
        mockMvc.perform(get("/api/meetings/{moimId}", 1L)
                        .param("meetingStatus", String.valueOf(PAST))
                )
                .andExpect(status().isOk())
                .andDo(document("모임내 이전 미팅 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 목록 조회")
                                .queryParameters(
                                        parameterWithName("meetingStatus").description(
                                                String.format("미팅 요일 - %s", EnumDocsUtils.getEnumNames(MeetingStatus.class))
                                        )
                                )
                                .responseFields(
                                        fieldWithPath("data[0].meetingId").type(NUMBER).description("미팅 id"),
                                        fieldWithPath("data[0].agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("data[0].startDate").type(STRING).description("미팅 시작 날짜"),
                                        fieldWithPath("data[0].dayOfWeek").type(VARIES).description(
                                                String.format("미팅 요일 - %s", EnumDocsUtils.getEnumNames(DayOfWeek.class))
                                        ),
                                        fieldWithPath("data[0].dDay").type(NUMBER).description("D-Day")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임내 모든 미팅 정보를 조회한다.")
    @Test
    void findAllByMoimId() throws Exception {
        mockMvc.perform(get("/api/meetings/{moimId}", 1L)
                        .param("meetingStatus", String.valueOf(ALL))
                )
                .andExpect(status().isOk())
                .andDo(document("모임내 모든 미팅 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 목록 조회")
                                .queryParameters(
                                        parameterWithName("meetingStatus").description(
                                                String.format("미팅 요일 - %s", EnumDocsUtils.getEnumNames(MeetingStatus.class))
                                        )
                                )
                                .responseFields(
                                        fieldWithPath("data[0].meetingId").type(NUMBER).description("미팅 id"),
                                        fieldWithPath("data[0].agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("data[0].startDate").type(STRING).description("미팅 시작 날짜"),
                                        fieldWithPath("data[0].dayOfWeek").type(VARIES).description(
                                                String.format("미팅 요일 - %s", EnumDocsUtils.getEnumNames(DayOfWeek.class))
                                        ),
                                        fieldWithPath("data[0].dDay").type(NUMBER).description("D-Day")
                                )
                                .build()
                        )));
    }

    @DisplayName("미팅 상세 정보를 조회한다.")
    @Test
    void findDetailsById() throws Exception {
        mockMvc.perform(get("/api/meetings/detail/{meetingId}", 1L)
                )
                .andExpect(status().isOk())
                .andDo(document("미팅 상세 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 상세 조회")
                                .responseFields(
                                        fieldWithPath("meetingId").type(NUMBER).description("미팅 id"),
                                        fieldWithPath("agenda").type(STRING).description("미팅 의제"),
                                        fieldWithPath("startDateTime").type(STRING).description("미팅 시작 시간"),
                                        fieldWithPath("endDateTime").type(STRING).description("미팅 종료 시간"),
                                        fieldWithPath("place").type(STRING).description("미팅 장소"),
                                        fieldWithPath("members[0].memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("members[0].username").type(STRING).description("회원 이름"),
                                        fieldWithPath("members[0].memberProfileImageUrl").type(STRING)
                                                .description("프로필 이미지 url")
                                )
                                .build()
                        )));
    }
}