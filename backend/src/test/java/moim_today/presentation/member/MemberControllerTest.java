package moim_today.presentation.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
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
import org.springframework.mock.web.MockMultipartFile;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerTest extends ControllerTest {

    private final MemberService fakeMemberService = new FakeMemberService();

    @Override
    protected Object initController() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new MemberController(fakeMemberService);
    }

    @DisplayName("인증 토큰을 기반으로 비밀번호를 수정한다.")
    @Test
    void recoverPassword() throws Exception {
        PasswordRecoverRequest passwordRecoverRequest =
                new PasswordRecoverRequest(CERTIFICATION_TOKEN.value(), NEW_PASSWORD.value());
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
                new PasswordRecoverRequest(WRONG_CERTIFICATION_TOKEN.value(), NEW_PASSWORD.value());
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

    @DisplayName("프로필을 조회한다.")
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

    @DisplayName("프로필 정보를 수정한다.")
    @Test
    void updateProfile() throws Exception {

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(
                Long.parseLong(DEPARTMENT_ID.value()),
                PROFILE_IMAGE_URL.value()
        );

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
                                        fieldWithPath("departmentId").type(NUMBER).description("주 전공 아이디"),
                                        fieldWithPath("imageUrl").type(STRING).description("프로필 이미지 URL")
                                )
                                .build()
                        )));
    }

    @DisplayName("프로필 이미지를 업로드/수정하면 업로드/수정된 파일의 URL을 반환한다.")
    @Test
    void updateProfileImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                FILE_NAME.value(),
                ORIGINAL_FILE_NAME.value(),
                TEXT_PLAIN_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(multipart("/api/members/profile-image")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("프로필 이미지 업로드/수정 성공",
                        requestParts(
                                partWithName("file").description("프로필 이미지")
                        )
                        , resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("프로필 이미지 업로드/수정")
                                .responseFields(
                                        fieldWithPath("imageUrl").type(STRING).description("업로드된 프로필 이미지 URL")
                                )
                                .build()
                        )
                ));
    }

    @DisplayName("멤버가 모임의 호스트인지 검사한다")
    @Test
    void isHostTest() throws Exception {
        mockMvc.perform(
                        get("/api/members/{moimId}/hosts", 1L)
                                .param("moimId", MOIM_ID.value())
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("모임의 호스트인지 검사",
                        pathParameters(
                                parameterWithName("moimId").description("모임 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("모임의 호스트인지 검사")
                                .responseFields(
                                        fieldWithPath("isHost").type(BOOLEAN).description("호스트 여부")
                                )
                                .build()
                        )));
    }

}
