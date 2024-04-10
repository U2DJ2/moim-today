package moim_today.persistence.repository.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentJpaRepository;

    public DepartmentRepositoryImpl(final DepartmentJpaRepository departmentJpaRepository) {
        this.departmentJpaRepository = departmentJpaRepository;
    }

    @Override
    public void save(final DepartmentJpaEntity departmentJpaEntity) {
        departmentJpaRepository.save(departmentJpaEntity);
    }
}
