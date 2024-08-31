package moim_today.implement.department.department;

import lombok.extern.slf4j.Slf4j;
import moim_today.domain.department.Department;
import moim_today.global.annotation.Implement;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Implement
public class DepartmentAppender {

    private final UniversityFinder universityFinder;
    private final DepartmentFetcher departmentFetcher;
    private final DepartmentRepository departmentRepository;

    public DepartmentAppender(final UniversityFinder universityFinder,
                              final DepartmentFetcher departmentFetcher,
                              final DepartmentRepository departmentRepository) {
        this.universityFinder = universityFinder;
        this.departmentFetcher = departmentFetcher;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void putAllDepartment() {
        List<String> allMajor = departmentFetcher.getAllMajor();
        Map<String, Set<String>> departmentUpdateQueue = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (String eachMajor : allMajor) {
            executorService.execute(() -> {
                departmentFetcher.patchDepartmentQueue(departmentUpdateQueue, eachMajor);
            });
        }
        shutdownAndAwaitTermination(executorService);

        batchUpdate(filterKnownUniversities(departmentUpdateQueue));
    }

    @Transactional
    public void batchUpdate(final List<DepartmentJpaEntity> departmentJpaEntities) {
        departmentRepository.batchUpdate(departmentJpaEntities);
    }

    @Transactional(readOnly = true)
    public List<DepartmentJpaEntity> filterKnownUniversities(final Map<String, Set<String>> universityAndDepartments) {
        List<UniversityJpaEntity> universities = universityFinder
                .findUniversitiesByName(universityAndDepartments.keySet().stream().toList());
        return Department.toEntities(universityAndDepartments, universities);
    }

    @Transactional
    public void addDepartment(final long universityId, final String requestDepartmentName) {
        Optional<DepartmentJpaEntity> optionalDepartmentJpaEntity =
                departmentRepository.findByUniversityIdAndDepartmentName(universityId, requestDepartmentName);

        if (optionalDepartmentJpaEntity.isEmpty()) {
            DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.toEntity(universityId, requestDepartmentName);
            departmentRepository.save(departmentJpaEntity);
        }
    }

    private void shutdownAndAwaitTermination(final ExecutorService executorService) {
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void logSizeOfMap(final Map<String, Set<String>> departmentUpdateQueue) {
        long totalSize = 0;

        for (Map.Entry<String, Set<String>> entry : departmentUpdateQueue.entrySet()) {
            totalSize += (entry.getValue().size() * 4L);
        }

        log.debug("size of departmentUpdateQueue: {}kb", totalSize / 1024);
    }
}
