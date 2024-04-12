package moim_today.implement.university;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static moim_today.global.constant.NumberConstant.MAX_SLASH_CNT;
import static moim_today.global.constant.SymbolConstant.*;
import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

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
            List<String> universityType = List.of(ASSOCIATE_DEGREE.value(),GRADUATE_DEGREE.value());

            for (JsonNode item : content) {
                String link = item.get(LINK.value()).asText();
                String schoolName = item.get(SCHOOL_NAME.value()).asText();
                String schoolType = item.get(SCHOOL_TYPE.value()).asText();
                if(!universityType.contains(schoolType)){
                    continue;
                }

               link = extractEmailExtension(link);

                UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                        .universityEmail(link)
                        .universityName(schoolName)
                        .build();
                universityRepository.put(universityJpaEntity);
            }
        } catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    private String extractEmailExtension(String link){
        // 'http://' 또는 'https://' 부분 제거
        int slashCnt = 0;
        while(slashCnt < MAX_SLASH_CNT.value()){
            int index = link.indexOf(SLASH.value());
            if(index == -1){
                break;
            }
            link = link.substring(index+1);
            slashCnt++;
        }

        // 앞에 www. 제거
        int wwwIndex = link.indexOf(WWW.value());
        if(wwwIndex != -1){
            link = link.substring(wwwIndex+WWW.value().length());
        }

        // ac.kr 뒷부분 제거
        int ackrIndex = link.indexOf(EMAIL_EXTENSION.value());
        if(ackrIndex != -1){
            link = link.substring(0, ackrIndex + EMAIL_EXTENSION.value().length());
        }

        return link;
    }
}
