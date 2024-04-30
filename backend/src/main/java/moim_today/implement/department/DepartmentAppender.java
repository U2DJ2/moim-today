package moim_today.implement.department;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Implement
public class DepartmentAppender {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UniversityFinder universityFinder;
    private final DepartmentRepository departmentRepository;

    public DepartmentAppender(final RestTemplate restTemplate, final ObjectMapper objectMapper,
                              final UniversityFinder universityFinder, final DepartmentRepository departmentRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.universityFinder = universityFinder;
        this.departmentRepository = departmentRepository;
    }

    @Value("${university.api.key}")
    private String apiKey;

    @Transactional
    public void putAllDepartment(){
        List<String> allDepartment = fetchAllDepartment();
        for(String eachDepartment : allDepartment){
            queryUniversitiesByDepartment(eachDepartment);
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
            }

            return allMajor;
        }catch(JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    @Transactional
    public List<DepartmentJpaEntity> queryUniversitiesByDepartment(final String majorSeq){
        String url = UNIVERSITY_API_URL.value()+apiKey+FETCH_ALL_UNIVERSITY_BY_DEPARTMENT_URL.value()+majorSeq;
        String response = restTemplate.getForObject(url, String.class);

        List<DepartmentJpaEntity> returnDepartmentJpaEntity = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                JsonNode universities = item.path(UNIVERSITY.value());
                for (JsonNode university : universities) {
                    String departmentName = university.path(MAJOR_NAME.value()).asText();
                    String universityName = university.path(SCHOOL_NAME.value()).asText();

                    Optional<UniversityJpaEntity> universityJpaEntity = universityFinder.findByName(universityName);
                    if(universityJpaEntity.isPresent()){
                       saveDepartment(universityJpaEntity.get(), departmentName);
                    }
                }
            }
        }catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }

        return returnDepartmentJpaEntity;
    }

    @Transactional
    public List<UniversityJpaEntity> findUniversitiesByName(final List<String> universityName){
        return universityFinder.findUniversitiesByName(universityName);
    }

    @Transactional
    public void batchUpdate(List<DepartmentJpaEntity> departmentJpaEntities){
        departmentRepository.batchUpdate(departmentJpaEntities);
    }

    @Transactional
    public void saveDepartment(UniversityJpaEntity universityJpaEntity, String departmentName){
        DepartmentJpaEntity saveDepartment = DepartmentJpaEntity.builder()
                .universityId(universityJpaEntity.getId())
                .departmentName(departmentName)
                .build();
        departmentRepository.save(saveDepartment);
    }
}
