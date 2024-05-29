package moim_today.persistence.repository.department.request_department;

import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;

import java.util.List;


public interface RequestDepartmentRepository {

    List<RequestDepartmentResponse> findAllByUniversityId(final long universityId);

    void save(final RequestDepartmentJpaEntity requestDepartmentJpaEntity);

    void deleteById(final long requestDepartmentId);

    long count();
}
