package moim_today.persistence.repository.schedule;

import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, Long> {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);

    List<ScheduleJpaEntity> findAllByMemberId(final long memberId);
}
