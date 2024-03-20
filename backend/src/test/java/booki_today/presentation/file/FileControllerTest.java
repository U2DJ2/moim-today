package booki_today.presentation.file;

import booki_today.dto.file.FileDeleteRequest;
import booki_today.util.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest extends ControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("S3 파일 업로드 테스트")
    void uploadFiles() throws Exception{
        MockMultipartFile file1 = new MockMultipartFile("file", "file1.txt", MediaType.TEXT_PLAIN_VALUE, "Test File 1".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, "/api/files")
                        .file(new MockMultipartFile("fileAddRequest", "", "application/json", "{\"uploadFilePath\":\"booki\"}".getBytes(StandardCharsets.UTF_8)))
                        .file(file1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteFileTest() throws Exception {
        FileDeleteRequest fileDeleteRequest = new FileDeleteRequest("temp", "1111");

        mockMvc.perform(delete("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileDeleteRequest)))
                .andExpect(status().isOk());
    }
}