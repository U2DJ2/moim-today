package moim_today.presentation.meeting.meeting_comment;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.meeting.meeting_comment.MeetingCommentService;
import moim_today.dto.meeting.meeting_comment.MeetingCommentCreateRequest;
import moim_today.fake_class.meeting.meeting_comment.FakeMeetingCommentService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static moim_today.util.TestConstant.MEETING_COMMENT_CONTENTS;
import static moim_today.util.TestConstant.MEETING_ID;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeetingCommentControllerTest extends ControllerTest {

    private final MeetingCommentService meetingCommentService  = new FakeMeetingCommentService();

    @Override
    protected Object initController() {
        return new MeetingCommentController(meetingCommentService);
    }
    
    @DisplayName("미팅에 댓글을 작성한다.")
    @Test
    void createMeetingComment() throws Exception {
        MeetingCommentCreateRequest meetingCommentCreateRequest = MeetingCommentCreateRequest.builder()
                .meetingId(MEETING_ID.longValue())
                .contents(MEETING_COMMENT_CONTENTS.value())
                .build();

        mockMvc.perform(post("/api/meeting-comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingCommentCreateRequest)))
                .andExpect(status().isOk())
                .andDo(document("미팅 댓글 작성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅 댓글")
                                .summary("미팅 댓글 작성")
                                .requestFields(
                                        fieldWithPath("meetingId").type(NUMBER).description("미팅 Id"),
                                        fieldWithPath("contents").type(STRING).description("댓글 내용")
                                )
                                .build()
                        )));
    }

    @DisplayName("미팅의 댓글 목록을 불러온다.")
    @Test
    void findAllMeetingCommentsByMeetingId() throws Exception {
        long meetingId = MEETING_ID.longValue();

        mockMvc.perform(get("/api/meeting-comments/{meetingId}", meetingId))
                .andExpect(status().isOk())
                .andDo(document("미팅 댓글 목록 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅 댓글")
                                .summary("미팅 댓글 목록 조회")
                                .responseFields(
                                        fieldWithPath("data[0].meetingCommentId").type(NUMBER).description("댓글 Id"),
                                        fieldWithPath("data[0].username").type(STRING).description("작성자"),
                                        fieldWithPath("data[0].imageUrl").type(STRING).description("작성자 프로필 사진 Url"),
                                        fieldWithPath("data[0].contents").type(STRING).description("댓글 내용"),
                                        fieldWithPath("data[0].createdAt").type(STRING).description("생성 일자")
                                        )
                                .build()
                        )));
    }
}