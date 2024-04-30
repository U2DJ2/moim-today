package moim_today.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import moim_today.application.member.MemberService;
import moim_today.dto.member.PasswordRecoverRequest;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.fake_class.member.FakeMemberService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerTest extends ControllerTest {

    private final MemberService memberService = new FakeMemberService();

    @Override
    protected Object initController() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new MemberController(memberService);
    }

    @DisplayName("인증 토큰을 기반으로 비밀번호를 수정한다.")
    @Test
    void recoverPassword() throws Exception {
        PasswordRecoverRequest passwordRecoverRequest =
                new PasswordRecoverRequest(CERTIFICATION_TOKEN.value(),NEW_PASSWORD.value());
        String json = objectMapper.writeValueAsString(passwordRecoverRequest);

        mockMvc.perform(post("/api/members/password-recovery")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("인증 토큰 기반 비밀번호 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("인증 토큰 기반 비밀번호 수정")
                                .requestFields(
                                        fieldWithPath("passwordToken").type(STRING).description("인증 토큰"),
                                        fieldWithPath("newPassword").type(STRING).description("수정 비밀번호")
                                )
                                .build()
                        )));
    }

    @DisplayName("인증 토큰이 일치하지 않으면 비밀번호 수정에 실패한다")
    @Test
    void recoverPasswordFail() throws Exception {
        PasswordRecoverRequest passwordRecoverRequest =
                new PasswordRecoverRequest(WRONG_CERTIFICATION_TOKEN.value(),NEW_PASSWORD.value());
        String json = objectMapper.writeValueAsString(passwordRecoverRequest);

        mockMvc.perform(post("/api/members/password-recovery")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("인증 토큰 기반 비밀번호 수정 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("인증 토큰 기반 비밀번호 수정")
                                .requestFields(
                                        fieldWithPath("passwordToken").type(STRING).description("인증 토큰"),
                                        fieldWithPath("newPassword").type(STRING).description("수정 비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("비밀번호 수정에 성공한다.")
    @Test
    void updatePassword() throws Exception {
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(NEW_PASSWORD.value());
        String json = objectMapper.writeValueAsString(passwordUpdateRequest);

        mockMvc.perform(patch("/api/members/password")
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

    @DisplayName("프로필을 조회에 성공한다.")
    @Test
    void getMemberProfile() throws Exception {
        mockMvc.perform(get("/api/members/profile"))
                .andExpect(status().isOk())
                .andDo(document("프로필 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("프로필 조회")
                                .responseFields(
                                        fieldWithPath("universityName").type(STRING).description("대학명"),
                                        fieldWithPath("departmentName").type(STRING).description("주 전공"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("username").type(STRING).description("이름"),
                                        fieldWithPath("studentId").type(STRING).description("학번"),
                                        fieldWithPath("birthDate").type(STRING).description("생일"),
                                        fieldWithPath("gender").type(STRING).description("성별"),
                                        fieldWithPath("memberProfileImageUrl").type(STRING).description("프로필 이미지 url")
                                )
                                .build()
                        )));
    }

    @DisplayName("프로필을 정보를 수정한다.")
    @Test
    void updateProfile() throws Exception {
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(1L);
        String json = objectMapper.writeValueAsString(profileUpdateRequest);

        mockMvc.perform(patch("/api/members/profile")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("프로필 정보 수정 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("프로필 정보 수정")
                                .requestFields(
                                        fieldWithPath("departmentId").type(NUMBER).description("주 전공 아이디")
                                )
                                .build()
                        )));
    }

    @DisplayName("프로필 이미지를 수정하면 회원의 프로필 이미지 URL 정보가 변경된다.")
    @Test
    void updateProfileImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                FILE_NAME.value(),
                ORIGINAL_FILE_NAME.value(),
                TEXT_PLAIN_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PATCH, "/api/members/profile-image")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("프로필 이미지 업로드/수정 성공",
                        requestParts(
                                partWithName("file").description("프로필 이미지")
                        )
                ));
    }
}
