package moim_today.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.fake_DB.FakeMemberSession;
import moim_today.fake_class.member.FakeMemberService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.global.constant.MemberSessionConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerTest extends ControllerTest {

    private final MemberService memberService = new FakeMemberService();

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @DisplayName("비밀번호 수정에 성공한다.")
    @Test
    void updatePassword() throws Exception {
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest("newPassword");
        String json = objectMapper.writeValueAsString(passwordUpdateRequest);
        MockHttpSession session = new MockHttpSession();
        MemberSession memberSession = FakeMemberSession.createMemberSession();
        session.setAttribute(MEMBER_SESSION.value(), objectMapper.writeValueAsString(memberSession));

        mockMvc.perform(patch("/api/members/password")
                        .session(session)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("비밀번호 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("비밀번호 수정")
                                .requestFields(
                                        fieldWithPath("newPassword").type(STRING).description("수정 비밀번호")
                                )
                                .build()
                        )));
    }
}