package moim_today.implement.department;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Implement
public class DepartmentAppender {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UniversityRepository universityRepository;
    private final DepartmentRepository departmentRepository;

    public DepartmentAppender(final RestTemplate restTemplate, final ObjectMapper objectMapper,
                              final UniversityRepository universityRepository, final DepartmentRepository departmentRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.universityRepository = universityRepository;
        this.departmentRepository = departmentRepository;
    }

    @Value("${university.api.key}")
    private String apiKey;

    public void putAllDepartment(){
        List<String> allDepartment = fetchAllDepartment();
        for(String eachDepartment : allDepartment){
            fetchUniversitiesByDepartment(eachDepartment);
        }
    }

    public List<String> fetchAllDepartment(){
        String url = UNIVERSITY_API_URL.value()+apiKey+FETCH_ALL_DEPARTMENT_URL.value();
        String response = restTemplate.getForObject(url, String.class);

        List<String> allMajor = new ArrayList<>();
        try{
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for(JsonNode item : content){
                String majorSeq = item.get(MAJOR_SEQ.value()).asText();
                allMajor.add(majorSeq);
                System.out.println(majorSeq);
            }

            return allMajor;
        }catch(JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    public void fetchUniversitiesByDepartment(final String majorSeq){
        String url = UNIVERSITY_API_URL.value()+apiKey+FETCH_ALL_UNIVERSITY_BY_DEPARTMENT_URL.value()+majorSeq;

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                JsonNode universities = item.path(UNIVERSITY.value());
                for (JsonNode university : universities) {
                    String departmentName = university.path(MAJOR_NAME.value()).asText();
                    String schoolName = university.path(SCHOOL_NAME.value()).asText();

                    UniversityJpaEntity universityJpaEntity = universityRepository.findByName(schoolName);
                    if(universityJpaEntity != null){
                        DepartmentJpaEntity saveDepartment = DepartmentJpaEntity.builder()
                                .universityId(universityJpaEntity.getId())
                                .departmentName(departmentName)
                                .build();
                        departmentRepository.save(saveDepartment);
                    }
                }
            }
        }catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }
}
