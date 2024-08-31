package moim_today.domain.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;

import java.util.*;

public record Department(
        long universityId,
        String departmentName
) {

    public static List<DepartmentJpaEntity> toEntities(final Map<String, Set<String>> universityAndDepartments,
                                                       final List<UniversityJpaEntity> universityJpaEntities) {
        Map<Long, Set<String>> knownUniversities = filterKnownUniversity(universityAndDepartments, universityJpaEntities);
        List<DepartmentJpaEntity> departmentJpaEntities = new ArrayList<>();

        for (Map.Entry<Long, Set<String>> entrySet : knownUniversities.entrySet()) {
            for (String departmentName : entrySet.getValue()) {
                departmentJpaEntities.add(DepartmentJpaEntity.builder()
                        .universityId(entrySet.getKey())
                        .departmentName(departmentName)
                        .build());
            }
        }
        return departmentJpaEntities;
    }

    public static Map<Long, Set<String>> filterKnownUniversity(final Map<String, Set<String>> universityAndDepartments,
                                                               final List<UniversityJpaEntity> universityJpaEntities) {
        Map<Long, Set<String>> existingUniversities = new HashMap<>();

        universityJpaEntities.forEach(universityJpaEntity -> {
            Long universityId = universityJpaEntity.getId();
            String universityName = universityJpaEntity.getUniversityName();

            Set<String> departments = universityAndDepartments.get(universityName);

            if (departments != null) {
                existingUniversities.computeIfAbsent(universityId, k -> new HashSet<>())
                        .addAll(departments);
            }
        });


        return existingUniversities;
    }

}
