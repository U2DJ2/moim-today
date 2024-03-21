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

    @DisplayName("S3 파일 업로드 테스트")
    @Test
    void uploadFileTest() throws Exception{

    }

    @DisplayName("파일 삭제 테스트")
    @Test
    void deleteFileTest() throws Exception {

    }
}