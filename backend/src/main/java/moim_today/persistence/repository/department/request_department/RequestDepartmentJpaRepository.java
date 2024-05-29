package moim_today.persistence.repository.department.request_department;

import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDepartmentJpaRepository extends JpaRepository<RequestDepartmentJpaEntity, Long> {
}
