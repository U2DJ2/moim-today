package moim_today.implement.university;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.domain.university.ExtractUniversity;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Implement
public class UniversityUpdater {

    private final UniversityRepository universityRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${university.api.key}")
    private String apiKey;

    public UniversityUpdater(final UniversityRepository universityRepository,
                             final ObjectMapper objectMapper, final RestTemplate restTemplate) {
        this.universityRepository = universityRepository;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void fetchAllUniversity() {
        String url = UNIVERSITY_API_URL.value() + apiKey + FETCH_ALL_UNIVERSITY_URL.value();
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                ExtractUniversity extractUniversity = objectMapper.readValue(item.toPrettyString(), ExtractUniversity.class);
                if (!extractUniversity.checkUniversityType()) {
                    continue;
                }
                extractUniversity.extractEmailExtension();
                putUniversity(extractUniversity);
            }
        } catch (JsonProcessingException e) {
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    @Transactional
    public void putUniversity(final ExtractUniversity extractUniversity) {
        Optional<UniversityJpaEntity> findUniversity = universityRepository.findByName(extractUniversity.getSchoolName());
        String adminPassword = extractUniversity.createAdminPassword();

        if (findUniversity.isEmpty()) {
            universityRepository.save(extractUniversity.toEntity(adminPassword));
        } else if (!extractUniversity.isEmailEmpty()) {
            UniversityJpaEntity getUniversity = findUniversity.get();
            getUniversity.updateEmail(extractUniversity.getLink());
            getUniversity.updateAdminPassword(adminPassword);
            universityRepository.save(getUniversity);
        }
    }
}
