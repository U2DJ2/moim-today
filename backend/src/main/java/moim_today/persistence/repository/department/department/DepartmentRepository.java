package moim_today.persistence.repository.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.persistence.entity.department.DepartmentJpaEntity;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    DepartmentJpaEntity save(final DepartmentJpaEntity departmentJpaEntity);

    DepartmentJpaEntity getById(final long departmentId);

    Optional<DepartmentJpaEntity> findByUniversityIdAndDepartmentName(final long universityId, final String departmentName);

    List<DepartmentResponse> findAllDepartmentOfUniversity(final long universityId);

    void batchUpdate(List<DepartmentJpaEntity> departmentJpaEntities);

    long count();
}
