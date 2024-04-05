package moim_today.persistence.repository.department;

import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentJpaRepository;

    public DepartmentRepositoryImpl(final DepartmentJpaRepository departmentJpaRepository) {
        this.departmentJpaRepository = departmentJpaRepository;
    }
}
