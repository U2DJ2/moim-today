package moim_today.persistence.repository.department.department;

import moim_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentJpaEntity, Long> {

    List<DepartmentJpaEntity> findAllByUniversityId(final long universityId);
}
