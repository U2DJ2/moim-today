package moim_today.presentation.todo;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.todo.TodoService;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoCreateRequest;
import moim_today.dto.todo.TodoRemoveRequest;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.fake_class.todo.FakeTodoService;
import moim_today.util.ControllerTest;
import moim_today.util.EnumDocsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.YearMonth;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.fasterxml.jackson.databind.node.JsonNodeType.NUMBER;
import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static moim_today.domain.todo.enums.TodoProgress.COMPLETED;
import static moim_today.util.TestConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.VARIES;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoControllerTest extends ControllerTest {

    private final TodoService todoService = new FakeTodoService();

    private final String TODO_CONTENT = "투두에 들어갈 내용";
    private final LocalDate TODO_DATE =
            LocalDate.of(1200, 1, 1);

    @Override
    protected Object initController() {
        return new TodoController(todoService);
    }

    @DisplayName("Todo를 생성한다")
    @Test
    void createTodo() throws Exception {
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest(MOIM_ID.longValue(), TODO_CONTENT,
                TODO_DATE);
        String json = objectMapper.writeValueAsString(todoCreateRequest);

        mockMvc.perform(
                        post("/api/todos")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("투두 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 생성")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("moimId").type(STRING).description("모임 id"),
                                        fieldWithPath("contents").type(NUMBER).description("투두 내용"),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("todoId").type(STRING).description("생성된 투두 id")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("모임에 참여한 멤버가 아닐 경우 Todo 생성에 실패한다")
    @Test
    void createTodoNotInMoimError() throws Exception {
        long memberId = 1L;
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest(MOIM_ID.longValue() + 1L, TODO_CONTENT,
                TODO_DATE);
        String json = objectMapper.writeValueAsString(todoCreateRequest);

        mockMvc.perform(
                        post("/api/todos")
                                .contentType(APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("모임에 없는 멤버라 투두 생성 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 생성")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("moimId").type(STRING).description("모임 id"),
                                        fieldWithPath("contents").type(NUMBER).description("투두 내용"),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("모임 안에 있는 모든 Todo를 조회한다")
    @Test
    void findAllMembersTodosInMoim() throws Exception {
        long memberId = 1L;
        final String TWO_MONTHS = "2";

        mockMvc.perform(
                        get("/api/todos/moim/{moimId}", MOIM_ID.longValue())
                                .contentType(APPLICATION_JSON)
                                .queryParam("todoDate", String.valueOf(
                                        YearMonth.of(2024, 05)
                                ))
                                .queryParam("months", TWO_MONTHS)
                )
                .andExpect(status().isOk())
                .andDo(document("모임 안에 모든 투두 조회",
                        pathParameters(
                                parameterWithName("moimId").description("모임 ID")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("모임의 모든 투두 조회")
                                .queryParameters(
                                        parameterWithName("todoDate").description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul")),
                                        parameterWithName("months").description("조회 할 n개월")
                                )
                                .responseFields(
                                        fieldWithPath("data[].memberId").type(NUMBER).description("요청한 멤버 id"),
                                        fieldWithPath("data[].todoResponses[].todoId").type(NUMBER).description("투두 id"),
                                        fieldWithPath("data[].todoResponses[].contents").type(NUMBER).description("투두 내용"),
                                        fieldWithPath("data[].todoResponses[].todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("data[].todoResponses[].todoDate").type(NUMBER).description("투두 시작 시간")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("모임 안에 있는 멤버가 아니여서 모임 안에 있는 Todo 조회 실패")
    @Test
    void findAllMembersTodosInMoimNotInMoimError() throws Exception {
        final String TWO_MONTHS = "2";

        mockMvc.perform(
                        get("/api/todos/moim/{moimId}", MOIM_ID.longValue() + 1L)
                                .contentType(APPLICATION_JSON)
                                .queryParam("todoDate", String.valueOf(
                                        YearMonth.of(2024, 5)
                                ))
                                .queryParam("months", TWO_MONTHS)
                )
                .andExpect(status().isNotFound())
                .andDo(document("모임에 참여한 멤버가 아니어서 투두 조회 실패",
                        pathParameters(
                                parameterWithName("moimId").description("모임 ID")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("모임의 모든 투두 조회")
                                .queryParameters(
                                        parameterWithName("todoDate").description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul")),
                                        parameterWithName("months").description("조회 할 n개월")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("Todo를 업데이트한다")
    @Test
    void updateTodo() throws Exception {

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(TODO_ID.longValue(), MOIM_ID.longValue(),
                UPDATE_AFTER_CONTENT.value(), COMPLETED, TODO_DATE);

        String json = objectMapper.writeValueAsString(todoUpdateRequest);

        mockMvc.perform(patch("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("투두 업데이트 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 업데이트")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id"),
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("contents").type(STRING).description("업데이트 할 투두 컨텐츠 값"),
                                        fieldWithPath("todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("contents").type(STRING).description("업데이트 할 투두 컨텐츠 값"),
                                        fieldWithPath("todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("투두에 참여한 멤버가 아니기 때문에 Todo를 업데이트 실패")
    @Test
    void updateTodoNotJoinedMemberError() throws Exception {

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(TODO_ID.longValue(), MOIM_ID.longValue()+1L,
                UPDATE_AFTER_CONTENT.value(), COMPLETED, TODO_DATE);

        String json = objectMapper.writeValueAsString(todoUpdateRequest);

        mockMvc.perform(patch("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("투두에 참여하지 않은 멤버라 투두 업데이트 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 업데이트")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id"),
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("contents").type(STRING).description("업데이트 할 투두 컨텐츠 값"),
                                        fieldWithPath("todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("투두의 시작 날짜가 끝나는 날짜보다 뒤이기 때문에 Todo를 업데이트 실패")
    @Test
    void updateTodoStartDateError() throws Exception {

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(TODO_ID.longValue(), MOIM_ID.longValue()+2L,
                UPDATE_AFTER_CONTENT.value(), COMPLETED, TODO_DATE);

        String json = objectMapper.writeValueAsString(todoUpdateRequest);

        mockMvc.perform(patch("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(document("투두 시작 날짜 설정 오류로 에러",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 업데이트")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id"),
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("contents").type(STRING).description("업데이트 할 투두 컨텐츠 값"),
                                        fieldWithPath("todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("투두의 주인이 아니기 때문에 Todo를 업데이트 실패")
    @Test
    void updateTodoNotOwner() throws Exception {

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(TODO_ID.longValue(), MOIM_ID.longValue()+3L,
                UPDATE_AFTER_CONTENT.value(), COMPLETED, TODO_DATE);

        String json = objectMapper.writeValueAsString(todoUpdateRequest);

        mockMvc.perform(patch("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isForbidden())
                .andDo(document("투두 주인이 아니라 업데이트 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 업데이트")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id"),
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("contents").type(STRING).description("업데이트 할 투두 컨텐츠 값"),
                                        fieldWithPath("todoProgress").type(VARIES).description(
                                                String.format("투두 진행 상황 - %s", EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                                .attributes(key("format").value("yyyy-MM-dd"),
                                                        key("timezone").value("Asia/Seoul"))
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("Todo를 삭제한다")
    @Test
    void deleteTodo() throws Exception{
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(TODO_ID.longValue());

        String json = objectMapper.writeValueAsString(todoRemoveRequest);

        mockMvc.perform(delete("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(document("투두 삭제 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 삭제")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("없는 투두 id면 Todo 삭제에 실패한다")
    @Test
    void deleteTodoNotFoundError() throws Exception{
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(TODO_ID.longValue()+1L);

        String json = objectMapper.writeValueAsString(todoRemoveRequest);

        mockMvc.perform(delete("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(document("이미 삭제되거나 없는 투두여서 삭제 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 삭제")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("투두의 주인이 아니면 Todo 삭제에 실패한다")
    @Test
    void deleteTodoNotOwnerError() throws Exception{
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(TODO_ID.longValue()+2L);

        String json = objectMapper.writeValueAsString(todoRemoveRequest);

        mockMvc.perform(delete("/api/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isForbidden())
                .andDo(document("투두의 주인이 아니라서 삭제 실패",
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 삭제")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(NUMBER).description("투두 id")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("오류 메세지")
                                )
                                .build()
                        ))
                );
    }

    @DisplayName("하나의 Todo를 조회한다")
    @Test
    void getTodoById() throws Exception{


        mockMvc.perform(get("/api/todos/{todoId}", TODO_ID.longValue())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("투두 조회 성공",
                        pathParameters(
                                parameterWithName("todoId").description("조회할 투두 id")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("투두")
                                .summary("투두 조회")
                                .requestHeaders(
                                        headerWithName("Content-Type").description("application/json")
                                )
                                .responseFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 id"),
                                        fieldWithPath("contents").type(NUMBER).description("투두 내용"),
                                        fieldWithPath("todoProgress").type(NUMBER).description(
                                                String.format("투두 진행 - %s",
                                                        EnumDocsUtils.getEnumNames(TodoProgress.class))
                                        ),
                                        fieldWithPath("todoDate").type(NUMBER).description("투두 시작 시간")
                                )
                                .build()
                        ))
                );
    }
}
