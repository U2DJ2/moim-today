package moim_today.implement.department;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.domain.department.Department;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public void putAllDepartment() {
        List<String> allMajor = findAllMajor();
        for (String eachMajor : allMajor) {
            updateDepartments(eachMajor);
        }
    }

    @Transactional
    public void batchUpdate(final List<DepartmentJpaEntity> departmentJpaEntities) {
        departmentRepository.batchUpdate(departmentJpaEntities);
    }

    private void updateDepartments(final String eachMajor) {
        Map<String, Set<String>> universityAndDepartments = getDepartmentsOfUniversity(eachMajor);
        Map<Long, Set<String>> universityIdAndDepartments = filterExistingUniversity(universityAndDepartments);
        List<DepartmentJpaEntity> departmentJpaEntities = Department.toEntities(universityIdAndDepartments);
        batchUpdate(departmentJpaEntities);
    }

    private Map<Long, Set<String>> filterExistingUniversity(final Map<String, Set<String>> universityAndDepartments) {
        List<UniversityJpaEntity> universityJpaEntities = universityFinder
                .findUniversitiesByName(universityAndDepartments.keySet().stream().toList());

        return Department.convertToUnivIdAndDepartments(universityAndDepartments, universityJpaEntities);
    }

    private List<String> findAllMajor() {
        String url = UNIVERSITY_API_URL.value() + apiKey + FETCH_ALL_DEPARTMENT_URL.value();
        String response = restTemplate.getForObject(url, String.class);

        List<String> allMajor = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                String majorSeq = item.get(MAJOR_SEQ.value()).asText();
                allMajor.add(majorSeq);
            }

            return allMajor;
        } catch (JsonProcessingException e) {
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    private Map<String, Set<String>> getDepartmentsOfUniversity(final String majorSeq) {
        String url = UNIVERSITY_API_URL.value() + apiKey + FETCH_ALL_UNIVERSITY_BY_DEPARTMENT_URL.value() + majorSeq;
        String response = restTemplate.getForObject(url, String.class);

        Map<String, Set<String>> universityAndDepartments = new HashMap<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                JsonNode universities = item.path(UNIVERSITY.value());
                for (JsonNode university : universities) {
                    String departmentName = university.path(MAJOR_NAME.value()).asText();
                    String universityName = university.path(SCHOOL_NAME.value()).asText();

                    patchMap(universityAndDepartments, universityName, departmentName);
                }
            }
        } catch (JsonProcessingException e) {
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }

        return universityAndDepartments;
    }

    private void patchMap(final Map<String, Set<String>> universityAndDepartments, final String universityName, final String departmentName) {
        Set<String> departments = universityAndDepartments
                .computeIfAbsent(universityName, k -> new HashSet<>());
        departments.add(departmentName);
    }
}
