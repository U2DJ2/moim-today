package booki_today.persistence.repository.category.category;

import booki_today.persistence.entity.category.category.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {
}
