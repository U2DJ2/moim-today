package moim_today.persistence.repository.schedule.schedule;

import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleJpaEntity, Long> {

    void deleteAllByMeetingIdIn(final List<Long> meetingIds);

    void deleteAllByMeetingId(final long meetingId);

    void deleteByMemberIdAndMeetingId(final long memberId, final long meetingId);

    void deleteAllByMemberId(final long memberId);

    List<ScheduleJpaEntity> findAllByMemberId(final long memberId);
}
