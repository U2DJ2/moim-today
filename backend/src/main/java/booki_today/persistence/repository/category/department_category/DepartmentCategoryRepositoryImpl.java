package booki_today.persistence.repository.category.department_category;

import org.springframework.stereotype.Repository;

@Repository
public class DepartmentCategoryRepositoryImpl implements DepartmentCategoryRepository {

    private final DepartmentCategoryJpaRepository departmentCategoryJpaRepository;

    public DepartmentCategoryRepositoryImpl(final DepartmentCategoryJpaRepository departmentCategoryJpaRepository) {
        this.departmentCategoryJpaRepository = departmentCategoryJpaRepository;
    }
}
