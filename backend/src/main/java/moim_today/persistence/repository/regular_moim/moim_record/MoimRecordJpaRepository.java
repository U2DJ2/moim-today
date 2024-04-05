package moim_today.persistence.repository.regular_moim.moim_record;

import moim_today.persistence.entity.regular_moim.moim_record.MoimRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimRecordJpaRepository extends JpaRepository<MoimRecordJpaEntity, Long> {
}
