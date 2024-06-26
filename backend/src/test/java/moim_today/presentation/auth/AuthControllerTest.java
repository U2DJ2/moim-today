package moim_today.presentation.auth;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.auth.AuthService;
import moim_today.domain.member.enums.Gender;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.fake_class.auth.FakeAuthService;
import moim_today.util.ControllerTest;
import moim_today.util.EnumDocsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    private final AuthService authService = new FakeAuthService();

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("로그인에 성공한다.")
    @Test
    void loginTest() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value(), true);
        String json = objectMapper.writeValueAsString(memberLoginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("password").type(STRING).description("비밀번호"),
                                        fieldWithPath("isKeepLogin").type(BOOLEAN).description("로그인 유지 여부 체크")
                                )
                                .build()
                        )
                ));
    }

    @DisplayName("이메일/비밀번호가 틀리면 예외가 발생한다.")
    @Test
    void loginTestFail() throws Exception {
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(WRONG_EMAIL.value(), WRONG_PASSWORD.value(), true);
        String json = objectMapper.writeValueAsString(memberLoginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("로그인 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("password").type(STRING).description("비밀번호"),
                                        fieldWithPath("isKeepLogin").type(BOOLEAN).description("로그인 유지 여부 체크")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태코드"),
                                        fieldWithPath("message").type(STRING).description("메시지")
                                )
                                .build()
                        )
                ));
    }

    @DisplayName("로그아웃에 성공한다.")
    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(post("/api/logout"))
                .andExpect(status().isOk())
                .andDo(document("로그아웃 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증")
                                .summary("로그아웃")
                                .build()
                        )
                ));
    }

    @DisplayName("회원가입에 성공한다.")
    @Test
    void registerTest() throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email(AJOU_EMAIL.value())
                .password(PASSWORD.value())
                .universityId(Long.parseLong(UNIV_ID.value()))
                .departmentId(Long.parseLong(DEPARTMENT_ID.value()))
                .studentId(STUDENT_ID.value())
                .birthDate(LocalDate.now())
                .gender(Gender.MALE)
                .username(USERNAME.value())
                .build();

        String json = objectMapper.writeValueAsString(memberSignUpRequest);

        mockMvc.perform(post("/api/sign-up")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(document("회원가입 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증")
                                .summary("회원가입")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("학교 이메일"),
                                        fieldWithPath("password").type(STRING).description("비밀번호"),
                                        fieldWithPath("universityId").type(NUMBER).description("학교 ID"),
                                        fieldWithPath("departmentId").type(NUMBER).description("학과 ID"),
                                        fieldWithPath("studentId").type(STRING).description("학번"),
                                        fieldWithPath("birthDate").type(STRING).description("생년월일 (yyyy-MM-dd)"),
                                        fieldWithPath("gender").type(VARIES).description(String.format("성별 - %s",
                                                EnumDocsUtils.getEnumNames(Gender.class))),
                                        fieldWithPath("username").type(STRING).description("이름")
                                )
                                .build())
                ));
    }

    @DisplayName("로그인 세션이 유효한지를 검증한다.")
    @Test
    void validateMemberSession() throws Exception {
        mockMvc.perform(get("/api/session-validation"))
                .andExpect(status().isOk())
                .andDo(document("회원 세션 인증 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증")
                                .summary("회원 세션 인증")
                                .responseFields(
                                        fieldWithPath("isValidateMemberSession").type(BOOLEAN).description("세션 인증 여부")
                                )
                                .build()
                        )
                ));
    }
}
