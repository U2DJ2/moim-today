package moim_today.presentation.moim;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.MoimDetailRequest;
import moim_today.dto.moim.MoimUpdateRequest;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.fake_class.moim.FakeMoimService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MoimControllerTest extends ControllerTest {

    private final FakeMoimService fakeMoimService = new FakeMoimService();

    @Override
    protected Object initController() {
        return new MoimController(fakeMoimService);
    }

    @DisplayName("공개 모임 생성 테스트")
    @Test
    void createPublicMoimApiTest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 01);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        PublicMoimAppendRequest publicMoimAppendRequest = new PublicMoimAppendRequest(
                TITLE.value(),
                CONTENTS.value(),
                Integer.parseInt(CAPACITY.value()),
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                startDate,
                endDate
        );

        mockMvc.perform(post("/api/moims/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicMoimAppendRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("공개 모임 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("공개 모임 생성")
                                .requestFields(
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("moimCategory").type(VARIES).description("카테고리"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("비공개 모임 생성 테스트")
    @Test
    void createPrivateMoimApiTest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 03, 01);
        LocalDate endDate = LocalDate.of(2024, 06, 30);

        PrivateMoimAppendRequest privateMoimAppendRequest = new PrivateMoimAppendRequest(
                TITLE.value(),
                CONTENTS.value(),
                Integer.parseInt(CAPACITY.value()),
                PASSWORD.value(),
                MOIM_IMAGE_URL.value(),
                MoimCategory.STUDY,
                startDate,
                endDate
        );

        mockMvc.perform(post("/api/moims/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(privateMoimAppendRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("비공개 모임 생성 성공",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("비공개 모임 생성")
                                .requestFields(
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("password").type(STRING).description("모임 비밀번호"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("moimCategory").type(VARIES).description("카테고리"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 사진 업로드 테스트 성공")
    @Test
    void uploadMoimImageTest() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                FILE_NAME.value(),
                ORIGINAL_FILE_NAME.value(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(multipart("/api/moims/image")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("모임 사진 업로드 테스트",
                        requestParts(
                                partWithName("file").description("모임 사진 파일")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 사진 업로드 성공 테스트 입니다.")
                                .responseFields(
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 상제 정보 조회 테스트")
    @Test
    void getMoimDetailTest() throws Exception {
        MoimDetailRequest moimDetailRequest = new MoimDetailRequest(1L);

        mockMvc.perform(get("/api/moims/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(moimDetailRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("모임 상세 정보 조회",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 상세 정보 조회 API")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("모임 ID")
                                )
                                .responseFields(
                                        fieldWithPath("title").type(STRING).description("모임명"),
                                        fieldWithPath("contents").type(STRING).description("내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("모집 인원"),
                                        fieldWithPath("currentCount").type(NUMBER).description("현재 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("모임 사진 URL"),
                                        fieldWithPath("moimCategory").type(VARIES).description("카테고리"),
                                        fieldWithPath("displayStatus").type(VARIES).description("공개여부"),
                                        fieldWithPath("views").type(NUMBER).description("조회수"),
                                        fieldWithPath("startDate").type(STRING).description("시작 일자"),
                                        fieldWithPath("endDate").type(STRING).description("종료 일자")
                                )
                                .build()
                        )));
    }

    @DisplayName("모임 정보 업데이트 테스트")
    @Test
    void updateMoimTest() throws Exception {
        MoimUpdateRequest moimUpdateRequest = MoimUpdateRequest.builder()
                .moimId(Long.parseLong(MOIM_ID.value()))
                .title(TITLE.value())
                .contents(CONTENTS.value())
                .capacity(Integer.parseInt(CAPACITY.value()))
                .imageUrl(MOIM_IMAGE_URL.value())
                .password(PASSWORD.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PRIVATE)
                .startDate(LocalDate.of(2024,3,1))
                .endDate(LocalDate.of(2024,6,30))
                .build();

        mockMvc.perform(patch("/api/moims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moimUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(document("모임 정보 업데이트 테스트",
                        resource(ResourceSnippetParameters.builder()
                                .tag("모임")
                                .summary("모임 정보를 업데이트(수정)하는 테스트 입니다.")
                                .requestFields(
                                        fieldWithPath("moimId").type(NUMBER).description("수정할 모임의 ID"),
                                        fieldWithPath("title").type(STRING).description("수정한 모임명"),
                                        fieldWithPath("contents").type(STRING).description("수정한 내용"),
                                        fieldWithPath("capacity").type(NUMBER).description("수정한 모집 인원"),
                                        fieldWithPath("imageUrl").type(STRING).description("수정한 모임 사진 URL"),
                                        fieldWithPath("password").type(STRING).description("수정한 비밀번호"),
                                        fieldWithPath("moimCategory").type(VARIES).description("수정한 카테고리"),
                                        fieldWithPath("displayStatus").type(VARIES).description("수정한 공개여부"),
                                        fieldWithPath("startDate").type(STRING).description("수정한 시작일자"),
                                        fieldWithPath("endDate").type(STRING).description("수정한 종료일자")
                                ).build()
                        )));
    }
}
