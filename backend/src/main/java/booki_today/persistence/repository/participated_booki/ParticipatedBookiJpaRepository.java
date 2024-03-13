package booki_today.persistence.repository.participated_booki;

import booki_today.persistence.entity.participated_booki.ParticipatedBookiJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatedBookiJpaRepository extends JpaRepository<ParticipatedBookiJpaEntity, Long> {
}
