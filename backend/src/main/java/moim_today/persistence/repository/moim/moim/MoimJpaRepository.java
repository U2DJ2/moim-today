package moim_today.persistence.repository.moim.moim;

import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimJpaRepository extends JpaRepository<MoimJpaEntity, Long> {
}

