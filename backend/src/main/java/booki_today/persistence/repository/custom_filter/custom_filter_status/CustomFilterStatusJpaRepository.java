package booki_today.persistence.repository.custom_filter.custom_filter_status;

import booki_today.persistence.entity.custom_filter.custom_filter_status.CustomFilterStatusJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFilterStatusJpaRepository extends JpaRepository<CustomFilterStatusJpaEntity, Long> {
}
