package moim_today.presentation.certification;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.certification.CertificationService;
import moim_today.dto.certification_token.PasswordFindRequest;
import moim_today.fake_class.certification_token.FakeCertificationService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CertificationControllerTest extends ControllerTest {

    private final CertificationService certificationService = new FakeCertificationService();

    @Override
    protected Object initController() {
        return new CertificationController(certificationService);
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

        mockMvc.perform(post("/api/certification-token/password")
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
                                .build()
                        )));
    }
}