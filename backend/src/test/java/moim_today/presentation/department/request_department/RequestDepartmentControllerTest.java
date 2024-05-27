package moim_today.presentation.department.request_department;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.department.request_department.RequestDepartmentService;
import moim_today.dto.department.AddDepartmentRequest;
import moim_today.fake_class.department.request_department.FakeRequestDepartmentService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RequestDepartmentControllerTest extends ControllerTest {

    private final RequestDepartmentService requestDepartmentService = new FakeRequestDepartmentService();

    @Override
    protected Object initController() {
        return new RequestDepartmentController(requestDepartmentService);
    }

    @DisplayName("학과 추가 요청 정보를 모두 조회한다.")
    @Test
    void findAll() throws Exception {
        mockMvc.perform(
                        get("/api/admin/request-departments")
                )
                .andExpect(status().isOk())
                .andDo(document("학과 정보 추가를 요청을 조회한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("학과")
                                .summary("학과 정보 추가 요청 조회")
                                .responseFields(
                                        fieldWithPath("data[0].requestDepartmentId").type(NUMBER).description("학과 추가 요청 id"),
                                        fieldWithPath("data[0].universityId").type(NUMBER).description("대학교 id"),
                                        fieldWithPath("data[0].requestDepartmentName").type(STRING).description("추가 요청 학교명")
                                )
                                .build())
                ));
    }

    @DisplayName("학과 추가 요청 정보를 저장한다.")
    @Test
    void requestAddDepartment() throws Exception {
        AddDepartmentRequest addDepartmentRequest = AddDepartmentRequest.builder()
                .universityId(UNIV_ID.longValue())
                .departmentName(DEPARTMENT_NAME.value())
                .build();

        String json = objectMapper.writeValueAsString(addDepartmentRequest);

        mockMvc.perform(
                        post("/api/request-departments")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("학과 정보 추가를 요청한다.",
                        resource(ResourceSnippetParameters.builder()
                                .tag("학과")
                                .summary("학과 정보 추가 요청")
                                .requestFields(
                                        fieldWithPath("universityId").type(NUMBER).description("대학교 id"),
                                        fieldWithPath("departmentName").type(STRING).description("학과명")
                                )
                                .build())
                ));
    }
}