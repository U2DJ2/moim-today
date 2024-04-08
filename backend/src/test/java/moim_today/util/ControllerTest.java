package moim_today.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.fake_class.FakeInterceptor;
import moim_today.global.argumentresolver.MemberLoginArgumentResolver;
import moim_today.global.error.ApiRestControllerAdvice;
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
                .setCustomArgumentResolvers(new MemberLoginArgumentResolver())
                .addInterceptors(new FakeInterceptor(objectMapper))
                .apply(documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
