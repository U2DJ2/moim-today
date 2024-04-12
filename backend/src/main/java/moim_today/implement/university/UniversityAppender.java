package moim_today.implement.university;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.domain.university.ExtractUniversity;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Implement
public class UniversityAppender {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UniversityUpdater universityUpdater;

    @Value("${university.api.key}")
    private String apiKey;

    public UniversityAppender(final RestTemplate restTemplate, final ObjectMapper objectMapper, final UniversityUpdater universityUpdater) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.universityUpdater = universityUpdater;
    }

    public void fetchAllUniversity(){
        String url = UNIVERSITY_API_URL.value()+apiKey+FETCH_ALL_UNIVERSITY_URL.value();
        String response = restTemplate.getForObject(url, String.class);

        try{
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                ExtractUniversity extractUniversity = objectMapper.readValue(item.toPrettyString(), ExtractUniversity.class);
                if(!extractUniversity.checkUniversityType()){
                    continue;
                }
                extractUniversity.extractEmailExtension();
                universityUpdater.putUniversity(extractUniversity.toEntity());
            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }
}
