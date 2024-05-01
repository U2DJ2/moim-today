package moim_today.presentation.department;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.fake_class.department.FakeDepartmentService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static javax.management.openmbean.SimpleType.STRING;
import static moim_today.global.constant.StaticSymbolConstant.BLANK;
import static moim_today.global.constant.StaticSymbolConstant.NO_UNIVERSITY_ID;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DepartmentControllerTest extends ControllerTest {

    private final FakeDepartmentService fakeDepartmentService = new FakeDepartmentService();

    @Override
    protected Object initController() {
        return new DepartmentController(fakeDepartmentService);
    }

    @DisplayName("한 대학교의 모든 학과를 대학교 ID로 가져오는 API")
    @Test
    void getDepartmentsById() throws Exception {
        mockMvc.perform(get("/api/departments/university-id")
                        .param(UNIVERSITY_ID.value(), UNIV_ID.value())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("대학교 ID로 모든 학과 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("학과")
                                .summary("대학교 ID로 모든 학과 조회")
                                .requestFields(
                                        fieldWithPath("universityId").type(STRING).description("대학교 ID")
                                                .optional().attributes(key("defaultValue").value(NO_UNIVERSITY_ID))
                                )
                                .responseFields(
                                        fieldWithPath("[]").description("대학교 내 모든 학과의 정보 리스트"),
                                        fieldWithPath("[].departmentId").description("학과 ID"),
                                        fieldWithPath("[].departmentName").description("학과 이름"),
                                        fieldWithPath("[].universityId").description("대학교 ID").optional()
                                )
                                .build())
                ));
    }

    @DisplayName("한 대학교의 모든 학과를 대학교 이름으로 가져오는 API")
    @Test
    void getDepartmentsByUniversityName() throws Exception {
        mockMvc.perform(get("/api/departments/university-name")
                        .param(UNIVERSITY_NAME.value(), AJOU_UNIVERSITY_NAME.value())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("대학교 이름으로 모든 학과 조회 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("학과")
                                .summary("대학교 이름으로 모든 학과 조회")
                                .requestFields(
                                        fieldWithPath("universityName").type(STRING).description("대학교 이름")
                                                .optional().attributes(key("defaultValue").value(BLANK))
                                )
                                .responseFields(
                                        fieldWithPath("[]").description("대학교 내 모든 학과의 정보 리스트"),
                                        fieldWithPath("[].departmentId").description("학과 ID"),
                                        fieldWithPath("[].departmentName").description("학과 이름"),
                                        fieldWithPath("[].universityId").description("대학교 ID").optional()
                                )
                                .build())
                ));
    }
}