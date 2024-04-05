package moim_today.persistence.repository.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentJpaEntity, Long> {
}
