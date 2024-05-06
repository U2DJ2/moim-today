package moim_today.presentation.file;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import moim_today.application.file.FileService;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.fake_class.file.FakeFileService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.headers.HeaderDocumentation;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static moim_today.util.TestConstant.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest extends ControllerTest {

    private final FileService fileService = new FakeFileService();

    @Override
    protected Object initController() {
        return new FileController(fileService);
    }

    @DisplayName("S3에 파일을 업로드한다.")
    @Test
    void uploadFileTest() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                FILE_NAME.value(),
                ORIGINAL_FILE_NAME.value(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                FILE_CONTENT.value().getBytes()
        );

        mockMvc.perform(multipart("/api/files")
                        .file(file)
                )
                .andExpect(status().isOk())
                .andDo(document("파일 업로드",
                        requestParts(
                                partWithName("file").description("모임 사진 파일")
                        ),
                        resource(ResourceSnippetParameters.builder()
                                .tag("파일")
                                .summary("파일 업로드")
                                .requestHeaders(
                                        HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE)
                                                .description(MediaType.MULTIPART_FORM_DATA_VALUE)
                                )
                                .responseFields(
                                        fieldWithPath("uploadFileUrl").type(STRING).description("업로드 한 파일 열람할 url")
                                )
                                .build()
                        )));
    }

//    프론트 사용 불가 , 이용하려면 파일 url 로 정보 매핑할 수 있도록 테이블 추가 필요

    @DisplayName("S3 파일을 삭제한다.")
    @Test
    void deleteFileTest() throws Exception {

        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest("temp", "1111");

        mockMvc.perform(delete("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(document("파일 삭제 (프론트 사용 아직 불가, 요청 정보 얻을 수 있는 기능 부족)",
                        resource(ResourceSnippetParameters.builder()
                                .tag("파일")
                                .summary("파일 삭제")
                                .requestFields(
                                        fieldWithPath("uploadFilePath").type(STRING).description("파일 업로드 경로"),
                                        fieldWithPath("fileName").type(STRING).description("파일 이름")
                                )
                                .build())
                ));
    }
}

