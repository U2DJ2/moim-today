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
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Profile({"prod", "dev"})
@Implement
public class UniversityAppender {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UniversityRepository universityRepository;

    @Value("${university.api.key}")
    private String apiKey;

    public UniversityAppender(final RestTemplate restTemplate, final ObjectMapper objectMapper,
                              final UniversityRepository universityRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.universityRepository = universityRepository;
    }

    public void fetchAllUniversity(){
        String url = UNIVERSITY_API_URL.value()+apiKey+FETCH_ALL_UNIVERSITY_URL.value();
        String response = restTemplate.getForObject(url, String.class);

        try{
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                ExtractUniversity extractUniversity = objectMapper.readValue(item.asText(), ExtractUniversity.class);
                if(!extractUniversity.checkUniversityType()){
                    continue;
                }
                extractUniversity.extractEmailExtension();
                putUniversity(extractUniversity.toEntity());
            }
        } catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    public void putUniversity(UniversityJpaEntity universityJpaEntity){
        UniversityJpaEntity findUniversity = universityRepository.findByName(universityJpaEntity.getUniversityName());

        if(findUniversity == null){
            universityRepository.save(universityJpaEntity);
            return;
        }
        if(!universityJpaEntity.getUniversityEmail().isEmpty()){
            findUniversity.updateEmail(universityJpaEntity.getUniversityEmail());
            universityRepository.save(findUniversity);
        }
    }
}
