package moim_today.presentation.auth;

import moim_today.application.auth.AuthService;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.fake_class.auth.FakeAuthService;
import moim_today.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moim_today.util.TestConstant.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    private final AuthService authService = new FakeAuthService();

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("로그인 테스트")
    @Test
    void loginTest() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value());
        String json = objectMapper.writeValueAsString(memberLoginRequest);

        mockMvc.perform(post("/api/auth")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인 성공 템플릿 API",
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("로그인 성공 템플릿")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("password").type(STRING).description("비밀번호")
                                )
                                .build()
                        )));
    }

    @DisplayName("로그인 테스트 실패")
    @Test
    void loginTestFail() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(WRONG_EMAIL.value(), WRONG_PASSWORD.value());
        String json = objectMapper.writeValueAsString(memberLoginRequest);

        mockMvc.perform(post("/api/auth")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("로그인 실패 템플릿 API",
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인")
                                .summary("로그인 실패 템플릿")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("password").type(STRING).description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )));
    }
}