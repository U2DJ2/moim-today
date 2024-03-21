package booki_today.persistence.repository.custom_filter.custom_filter;

import booki_today.persistence.entity.custom_filter.custom_filter.CustomFilterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFilterJpaRepository extends JpaRepository<CustomFilterJpaEntity, Long> {
}
