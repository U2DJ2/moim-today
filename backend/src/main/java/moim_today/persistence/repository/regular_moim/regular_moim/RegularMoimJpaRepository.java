package moim_today.persistence.repository.regular_moim.regular_moim;

import moim_today.persistence.entity.regular_moim.regular_moim.RegularMoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularMoimJpaRepository extends JpaRepository<RegularMoimJpaEntity, Long> {
}
