package booki_today.persistence.repository.created_booki;

import booki_today.persistence.entity.created_booki.CreatedBookiJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatedBookiJpaRepository extends JpaRepository<CreatedBookiJpaEntity, Long> {
}
