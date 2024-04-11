package moim_today.persistence.repository.regular_moim.moim_meeting;

import moim_today.persistence.entity.regular_moim.moim_meeting.MoimMeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimMeetingJpaRepository extends JpaRepository<MoimMeetingJpaEntity, Long> {
}
