package moim_today.presentation.file;

import moim_today.application.file.FileService;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.fake_class.FakeFileService;
import moim_today.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest extends ControllerTest{

    private final FileService fileService = new FakeFileService();

    @Override
    protected Object initController() {
        return new FileController(fileService);
    }

    @DisplayName("S3 파일 업로드 테스트")
    @Test
    void uploadFileTest() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile("file", "file1.txt", MediaType.TEXT_PLAIN_VALUE, "Test File 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("fileAddRequest", "", "application/json", "{\"uploadFilePath\":\"booki\"}".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, "/api/files")
                        .file(file1)
                        .file(file2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk());
    }

    @DisplayName("파일 삭제 테스트")
    @Test
    void deleteFileTest() throws Exception {

        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest("temp", "1111");

        mockMvc.perform(delete("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileDeleteRequest)))
                .andExpect(status().isOk());
    }
}

