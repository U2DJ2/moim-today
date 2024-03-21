package booki_today.persistence.repository.university;

import booki_today.persistence.entity.university.UniversityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityJpaRepository extends JpaRepository<UniversityJpaEntity, Long> {
}
