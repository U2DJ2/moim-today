package moim_today.implement.university;

import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.annotation.Implement;
import org.springframework.web.client.RestTemplate;

@Implement
public class UniversityAppender {

    private final UniversityUpdater universityUpdater;

    public UniversityAppender(final RestTemplate restTemplate, final ObjectMapper objectMapper, final UniversityUpdater universityUpdater) {
        this.universityUpdater = universityUpdater;
    }
}
