package moim_today.presentation.moim;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.fake_class.moim.FakeMoimService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
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
                MediaType.TEXT_PLAIN_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, "/api/moims/image")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("모임 사진 업로드",
                        requestParts(
                                partWithName("file").description("모임 사진 파일")
                        )
                ));
    }
}
