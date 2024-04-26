package moim_today.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import moim_today.fake_class.global.FakeInterceptor;
import moim_today.global.argumentresolver.MemberLoginArgumentResolver;
import moim_today.global.error.ApiRestControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static moim_today.util.TestConstant.LOCAL_DATE_FORMAT;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = serializingObjectMapper();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT.value());

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .setControllerAdvice(new ApiRestControllerAdvice())
                .setCustomArgumentResolvers(new MemberLoginArgumentResolver(objectMapper))
                .addInterceptors(new FakeInterceptor(objectMapper))
                .apply(documentationConfiguration(provider))
                .build();
    }

    private ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    private class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(final LocalDate value, JsonGenerator gen, final SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(FORMATTER));
        }
    }

    private class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), FORMATTER);
        }
    }

    protected abstract Object initController();
}