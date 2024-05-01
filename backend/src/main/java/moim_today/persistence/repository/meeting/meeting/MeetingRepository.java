package moim_today.persistence.repository.meeting.meeting;

import java.util.List;

public interface MeetingRepository {

    List<Long> findAllByMoimId(final long moimId);
}
