package moim_today.persistence.repository.department.request_department;

import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;

public interface RequestDepartmentRepository {

    void save(final RequestDepartmentJpaEntity requestDepartmentJpaEntity);

    long count();
}
