package moim_today.implement.department.department;

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

import static moim_today.global.constant.DepartmentConstant.DEPARTMENT_UPDATE_BATCH_SIZE;

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
        // 모든 학과 정보 조회
        List<String> allMajor = departmentFetcher.getAllMajor();

        // 업데이트 할 Department 정보
        Map<String, Set<String>> departmentUpdateQueue = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String eachMajor : allMajor) {
            executorService.execute(() -> {
                // Major의 모든 학과 정보 가져와서 대학교와 매핑하는 departmentUpdateQueue에 저장
                departmentFetcher.patchDepartmentQueue(departmentUpdateQueue, eachMajor);
                // 가져온 departmentUpdateQueue로 업뎃
                updateDepartmentsIfSizeOver(departmentUpdateQueue, DEPARTMENT_UPDATE_BATCH_SIZE.intValue());
            });
        }
        shutdownAndAwaitTermination(executorService);
        batchUpdate(filterUniversityExistToDepartment(departmentUpdateQueue));
    }

    @Transactional
    public synchronized void updateDepartmentsIfSizeOver(final Map<String, Set<String>> universityAndDepartments, final int size){
        if(getTotalMapSize(universityAndDepartments) > size){
            batchUpdate(filterUniversityExistToDepartment(universityAndDepartments));
            universityAndDepartments.clear();
        }
    }

    @Transactional
    public void batchUpdate(final List<DepartmentJpaEntity> departmentJpaEntities) {
        departmentRepository.batchUpdate(departmentJpaEntities);
    }

    @Transactional(readOnly = true)
    public List<DepartmentJpaEntity> filterUniversityExistToDepartment(final Map<String, Set<String>> universityAndDepartments) {
        // 대학교 Entity를 대학교 이름으로 가져옴
        List<UniversityJpaEntity> universities = universityFinder
                .findUniversitiesByName(universityAndDepartments.keySet().stream().toList());
        // 대학교 + 학과 정보와 대학교 Entity 정보를 추가하여 DepartmentJpaEnttiy로 변환
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

    private int getTotalMapSize(final Map<String, Set<String>> mapWithSet){
        int totalSize = 0;
        for(Map.Entry<String, Set<String>> entry : mapWithSet.entrySet()){
            totalSize += entry.getValue().size();
        }
        return totalSize;
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService) {
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

}
