package moim_today.presentation.meeting;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.meeting.MeetingService;
import moim_today.domain.meeting.enums.MeetingCategory;
import moim_today.dto.meeting.MeetingCreateRequest;
import moim_today.fake_class.meeting.FakeMeetingService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static moim_today.util.TestConstant.MEETING_PLACE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
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
}