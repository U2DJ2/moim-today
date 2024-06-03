package moim_today.presentation.meeting.joined_meeting;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.meeting.joined_meeting.JoinedMeetingService;
import moim_today.fake_class.meeting.joined_meeting.FakeJoinedMeetingService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JoinedMeetingControllerTest extends ControllerTest {

    private final JoinedMeetingService joinedMeetingService = new FakeJoinedMeetingService();

    @Override
    protected Object initController() {
        return new JoinedMeetingController(joinedMeetingService);
    }

    @DisplayName("회원이 특정 미팅 참여를 수락한다.")
    @Test
    void findAttendanceStatus() throws Exception {
        mockMvc.perform(
                        get("/api/members/meetings/{meetingId}", 1L)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 미팅 참여 여부를 조회한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 참여 여부 조회")
                                .responseFields(
                                        fieldWithPath("attendanceStatus").type(BOOLEAN).description("미팅 참여 여부")
                                )
                                .build()
                        )));
    }

    @DisplayName("회원이 특정 미팅 참여를 수락한다.")
    @Test
    void acceptanceJoinMeeting() throws Exception {
        mockMvc.perform(
                        post("/api/members/meetings/{meetingId}/acceptance", 1L)
                )
                .andExpect(status().isOk())
                .andDo(document("미팅 참여를 수락한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 참여 수락")
                                .build()
                        )));
    }

    @DisplayName("회원이 특정 미팅 참여를 거절한다.")
    @Test
    void refuseJoinMeeting() throws Exception {
        mockMvc.perform(
                        post("/api/members/meetings/{meetingId}/refusal", 1L)
                )
                .andExpect(status().isOk())
                .andDo(document("미팅 참여를 거절한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("미팅 참여 거절")
                                .build()
                        )));
    }
}