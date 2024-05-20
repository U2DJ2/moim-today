package moim_today.presentation.email_subscribe;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.email_subscribe.EmailSubscribeService;
import moim_today.dto.mail.EmailSubscribeRequest;
import moim_today.fake_class.email_subscribe.FakeEmailSubscribeService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmailSubscribeControllerTest extends ControllerTest {

    private final EmailSubscribeService emailSubscribeService = new FakeEmailSubscribeService();

    @Override
    protected Object initController() {
        return new EmailSubscribeController(emailSubscribeService);
    }

    @DisplayName("이메일 수신 여부 정보를 변경한다.")
    @Test
    void subscribeEmail() throws Exception {
        EmailSubscribeRequest emailSubscribeRequest = EmailSubscribeRequest.of(true);
        String json = objectMapper.writeValueAsString(emailSubscribeRequest);

        mockMvc.perform(
                        post("/api/email-subscription")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("이메일 수신 여부를 변경한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("미팅")
                                .summary("이메일 수신 정보 변경")
                                .requestFields(
                                        fieldWithPath("subscribeStatus").type(BOOLEAN).description("수신 여부")
                                )
                                .build()
                        )));
    }
}