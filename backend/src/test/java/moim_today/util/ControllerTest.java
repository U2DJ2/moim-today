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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static moim_today.util.TestConstant.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTest {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = serializingObjectMapper();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT.value());
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT.value());

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
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

    private static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(final LocalDate value, JsonGenerator gen, final SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(DATE_FORMATTER));
        }
    }

    private static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
            return LocalDate.parse(p.getValueAsString(), DATE_FORMATTER);
        }
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(final LocalDateTime value, final JsonGenerator gen, final SerializerProvider serializerProvider) throws IOException {
            gen.writeString(value.format(DATETIME_FORMATTER));
        }
    }

    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(final JsonParser p, final DeserializationContext deserializationContext) throws IOException {
            return LocalDateTime.parse(p.getValueAsString(), DATETIME_FORMATTER);
        }
    }

    protected abstract Object initController();
}