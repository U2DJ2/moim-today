package moim_today.implement.department.department;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static moim_today.global.constant.UniversityConstant.*;
import static moim_today.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;

@Implement
public class DepartmentFetcher {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DepartmentFetcher(final RestTemplate restTemplate, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Value("${university.api.key}")
    private String apiKey;

    public List<String> getAllMajor() {
        String url = UNIVERSITY_API_URL.value() + apiKey + FETCH_ALL_DEPARTMENT_URL.value();
        String response = restTemplate.getForObject(url, String.class);
        List<String> allMajor = new ArrayList<>();
        fetchAllMajor(response, allMajor);
        return allMajor;
    }

    public void patchDepartmentQueue(final Map<String, Set<String>> baseMap,
                                     final String majorSeq){
        Map<String, Set<String>> addingMap = getDepartmentsOfUniversity(majorSeq);
        for (Map.Entry<String, Set<String>> entry : addingMap.entrySet()) {
            baseMap.computeIfAbsent(entry.getKey(), k -> ConcurrentHashMap.newKeySet()).addAll(entry.getValue());
        }
    }

    private Map<String, Set<String>> getDepartmentsOfUniversity(final String majorSeq) {
        String url = UNIVERSITY_API_URL.value() + apiKey + FETCH_ALL_UNIVERSITY_BY_DEPARTMENT_URL.value() + majorSeq;
        String response = restTemplate.getForObject(url, String.class);
        return fetchAllDepartments(response);
    }

    private void fetchAllMajor(final String response, final List<String> allMajor) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path(DATA_SEARCH.value()).path(CONTENT.value());

            for (JsonNode item : content) {
                String majorSeq = item.get(MAJOR_SEQ.value()).asText();
                allMajor.add(majorSeq);
            }
        } catch (JsonProcessingException e) {
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    private Map<String, Set<String>> fetchAllDepartments(final String response) {
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
