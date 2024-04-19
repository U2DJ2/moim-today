package moim_today.presentation.certification;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.certification.email.EmailCertificationService;
import moim_today.application.certification.password.PasswordCertificationService;
import moim_today.dto.certification.CompleteEmailCertificationRequest;
import moim_today.dto.certification.EmailCertificationRequest;
import moim_today.dto.certification.PasswordFindRequest;
import moim_today.fake_class.certification.FakeEmailCertificationService;
import moim_today.fake_class.certification.FakePasswordCertificationService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CertificationControllerTest extends ControllerTest {

    private final PasswordCertificationService passwordCertificationService = new FakePasswordCertificationService();
    private final EmailCertificationService emailCertificationService = new FakeEmailCertificationService();

    @Override
    protected Object initController() {
        return new CertificationController(passwordCertificationService, emailCertificationService);
    }

    @DisplayName("비밀번호 찾기 메일을 전송한다.")
    @Test
    void sendPasswordFindMail() throws Exception {
        PasswordFindRequest passwordFindRequest = new PasswordFindRequest(EMAIL.value());
        String json = objectMapper.writeValueAsString(passwordFindRequest);

        mockMvc.perform(post("/api/certification/password")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("비밀번호 찾기 메일 전송 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("비밀번호 찾기 메일 전송")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .build()
                        )));
    }

    @DisplayName("메일이 존재하지 않으면 비밀번호 찾기 메일 전송에 실패한다.")
    @Test
    void sendPasswordFindMailFail() throws Exception {
        PasswordFindRequest passwordFindRequest = new PasswordFindRequest(WRONG_EMAIL.value());
        String json = objectMapper.writeValueAsString(passwordFindRequest);

        mockMvc.perform(post("/api/certification/password")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("메일이 존재하지 않으면 메일 전송 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("비밀번호 찾기 메일 전송")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("이메일 인증 메일을 전송한다.")
    @Test
    void sendCertificationEmail() throws Exception {
        EmailCertificationRequest emailCertificationRequest = new EmailCertificationRequest(EMAIL.value());
        String json = objectMapper.writeValueAsString(emailCertificationRequest);

        mockMvc.perform(post("/api/certification/email")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("이메일 인증 메일 전송 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("이메일 인증 메일 전송")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .build()
                        )));
    }

    @DisplayName("이미 존재하는 이메일로 인증을 시도하면 예외가 발생한다")
    @Test
    void sendCertificationEmailFail() throws Exception {
        EmailCertificationRequest emailCertificationRequest = new EmailCertificationRequest(WRONG_EMAIL.value());
        String json = objectMapper.writeValueAsString(emailCertificationRequest);

        mockMvc.perform(post("/api/certification/email")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("이미 가입된 메일이 존재하면 메일 전송 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("이메일 인증 메일 전송")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @DisplayName("이메일 인증이 완료되었으면 학교 정보를 반환한다.")
    @Test
    void completeCertification() throws Exception {
        CompleteEmailCertificationRequest completeEmailCertificationRequest =
                new CompleteEmailCertificationRequest(AJOU_EMAIL.value());
        String json = objectMapper.writeValueAsString(completeEmailCertificationRequest);

        mockMvc.perform(post("/api/certification/email/complete")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("이메일 인증 완료, 학교 정보 반환",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("이메일 인증 완료 여부 확인")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .responseFields(
                                        fieldWithPath("universityId").type(NUMBER).description("대학교 id"),
                                        fieldWithPath("universityName").type(STRING).description("대학교명")
                                )
                                .build()
                        )));
    }

    @DisplayName("이메일 인증이 완료되지 않았으면 예외가 발생한다.")
    @Test
    void completeCertificationFail() throws Exception {
        CompleteEmailCertificationRequest completeEmailCertificationRequest =
                new CompleteEmailCertificationRequest(WRONG_EMAIL.value());
        String json = objectMapper.writeValueAsString(completeEmailCertificationRequest);

        mockMvc.perform(post("/api/certification/email/complete")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("이메일 인증 완료되지 않음",
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 토큰")
                                .summary("이메일 인증 완료 여부 확인")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("이메일")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }
}