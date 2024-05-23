package moim_today.persistence.repository.department.request_department;

import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RequestDepartmentRepositoryImpl implements RequestDepartmentRepository {

    private final RequestDepartmentJpaRepository requestDepartmentJpaRepository;

    public RequestDepartmentRepositoryImpl(final RequestDepartmentJpaRepository requestDepartmentJpaRepository) {
        this.requestDepartmentJpaRepository = requestDepartmentJpaRepository;
    }

    @Override
    public void save(final RequestDepartmentJpaEntity requestDepartmentJpaEntity) {
        requestDepartmentJpaRepository.save(requestDepartmentJpaEntity);
    }
}
