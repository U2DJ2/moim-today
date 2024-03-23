package booki_today.persistence.repository.department;

import booki_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentJpaEntity, Long> {
}
