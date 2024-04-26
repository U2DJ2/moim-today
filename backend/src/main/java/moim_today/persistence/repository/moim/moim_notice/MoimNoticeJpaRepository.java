package moim_today.persistence.repository.moim.moim_notice;

import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimNoticeJpaRepository extends JpaRepository<MoimNoticeJpaEntity, Long> {
}
