package moim_today.implement.department.department;

import moim_today.domain.department.Department;
import moim_today.global.annotation.Implement;
import moim_today.implement.university.UniversityFinder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        List<String> allMajor = departmentFetcher.getAllMajor();
        Map<String, Set<String>> departmentUpdateQueue = new HashMap<>();

        for (String eachMajor : allMajor) {
            departmentFetcher.patchDepartmentQueue(departmentUpdateQueue, eachMajor);
            updateDepartmentsIfSizeOver(departmentUpdateQueue, DEPARTMENT_UPDATE_BATCH_SIZE.intValue());
        }
        batchUpdate(filterUniversityExistToDepartment(departmentUpdateQueue));
    }

    @Transactional
    public void batchUpdate(final List<DepartmentJpaEntity> departmentJpaEntities) {
        departmentRepository.batchUpdate(departmentJpaEntities);
    }

    @Transactional
    public void updateDepartmentsIfSizeOver(final Map<String, Set<String>> universityAndDepartments, final int size){
        if(getTotalMapSize(universityAndDepartments) > size){
            batchUpdate(filterUniversityExistToDepartment(universityAndDepartments));
            universityAndDepartments.clear();
        }
    }

    @Transactional(readOnly = true)
    public List<DepartmentJpaEntity> filterUniversityExistToDepartment(final Map<String, Set<String>> universityAndDepartments) {
        List<UniversityJpaEntity> universityIdAndDepartments = universityFinder
                .findUniversitiesByName(universityAndDepartments.keySet().stream().toList());
        return Department.toEntities(universityAndDepartments, universityIdAndDepartments);
    }

    private int getTotalMapSize(final Map<String, Set<String>> mapWithSet){
        int totalSize = 0;
        for(Map.Entry<String, Set<String>> entry : mapWithSet.entrySet()){
            totalSize += entry.getValue().size();
        }
        return totalSize;
    }
}
