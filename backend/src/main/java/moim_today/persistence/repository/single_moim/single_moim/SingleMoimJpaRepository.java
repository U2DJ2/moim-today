package moim_today.persistence.repository.single_moim.single_moim;

import moim_today.persistence.entity.single_moim.single_moim.SingleMoimJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleMoimJpaRepository extends JpaRepository<SingleMoimJpaEntity, Long> {
}
