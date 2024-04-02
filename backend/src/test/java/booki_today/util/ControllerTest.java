package booki_today.util;

import booki_today.global.error.ApiRestControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private final ApiRestControllerAdvice apiRestControllerAdvice = new ApiRestControllerAdvice();

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .setControllerAdvice(apiRestControllerAdvice)
                .apply(documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
