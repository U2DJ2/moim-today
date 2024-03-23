package booki_today.persistence.repository.category.university_category;

import booki_today.persistence.entity.category.university_category.UniversityCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityCategoryJpaRepository extends JpaRepository<UniversityCategoryJpaEntity, Long> {
}
