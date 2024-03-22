package booki_today.persistence.repository.category.department_category;

import booki_today.persistence.entity.category.department_category.DepartmentCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentCategoryJpaRepository extends JpaRepository<DepartmentCategoryJpaEntity, Long> {
}
