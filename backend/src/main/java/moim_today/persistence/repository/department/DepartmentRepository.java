package moim_today.persistence.repository.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;

import java.util.List;

public interface DepartmentRepository {

    void save(final DepartmentJpaEntity departmentJpaEntity);

    List<String> findAllDepartmentOfUniversity(final long universityId);

    DepartmentJpaEntity getById(final long departmentId);
}
