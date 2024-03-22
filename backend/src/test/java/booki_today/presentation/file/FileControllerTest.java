package booki_today.presentation.file;

import booki_today.application.file.FileService;
import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import booki_today.fake_class.FakeFileService;
import booki_today.util.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

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

