package moim_today.persistence.repository.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.persistence.entity.department.DepartmentJpaEntity;

import java.util.List;

public interface DepartmentRepository {

    void save(final DepartmentJpaEntity departmentJpaEntity);

    DepartmentJpaEntity getById(final long departmentId);
    List<DepartmentInfoResponse> findAllDepartmentOfUniversity(final long universityId);
}
