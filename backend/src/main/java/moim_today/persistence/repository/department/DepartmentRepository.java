package moim_today.persistence.repository.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;

public interface DepartmentRepository {

    void save(final DepartmentJpaEntity departmentJpaEntity);

    DepartmentJpaEntity getById(final long departmentId);
}
