package moim_today.presentation.university;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.fake_class.university.FakeUniversityService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UniversityControllerTest extends ControllerTest {

    private final FakeUniversityService fakeUniversityService = new FakeUniversityService();

    @Override
    protected Object initController() {
        return new UniversityController(fakeUniversityService);
    }

    @DisplayName("모든 대학교 정보를 가져오는 API")
    @Test
    void getUniversity() throws Exception{

        mockMvc.perform(get("/api/universities")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("모든 대학교 정보 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("대학교")
                                .summary("모든 대학교 정보 조회")
                                .responseFields(
                                        fieldWithPath("data").type(ARRAY).description("대학교 정보 리스트"),
                                        fieldWithPath("data[].universityId").type(NUMBER)
                                                .description("대학교 ID"),
                                        fieldWithPath("data[].universityName").type(STRING)
                                                .description("대학교 이름"),
                                        fieldWithPath("data[].universityEmail").type(STRING)
                                                .description("대학교 이메일 도메인")
                                )
                                .build()
                        )));
    }
}